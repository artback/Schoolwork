#include "playlistmodel.h"
PlaylistModel::PlaylistModel(QObject *parent) : QAbstractItemModel(parent), mPlaylist(0)
{

}
int PlaylistModel::rowCount(const QModelIndex &parent) const
{
    return mPlaylist && !parent.isValid() ? mPlaylist->mediaCount() : 0;
}

int PlaylistModel::columnCount(const QModelIndex &parent) const
{
    return !parent.isValid() ? ColumnCount : 0;
}

QModelIndex PlaylistModel::index(int row, int column, const QModelIndex &parent) const
{
    return mPlaylist && !parent.isValid()
            && row >= 0 && row < mPlaylist->mediaCount()
            && column >= 0 && column < ColumnCount
        ? createIndex(row, column)
        : QModelIndex();
}
QModelIndex PlaylistModel::parent(const QModelIndex &child) const{
    Q_UNUSED(child);
    return QModelIndex();
}
QVariant PlaylistModel::data(const QModelIndex &index, int role) const{
    if (index.isValid() && role == Qt::DisplayRole) {
        QVariant value = mData[index];
        if (!value.isValid() && index.column() == Title) {
            QUrl location = mPlaylist->media(index.row()).canonicalUrl();
            return QFileInfo(location.path()).fileName();
        }

        return value;
    }
    return QVariant();
}
QMediaPlaylist *PlaylistModel::playlist() const{
    return mPlaylist;
}
void PlaylistModel::setPlaylist(QMediaPlaylist *playlist){
    if (mPlaylist) {
        disconnect(mPlaylist, SIGNAL(mediaAboutToBeInserted(int,int)), this, SLOT(beginInsertItems(int,int)));
        disconnect(mPlaylist, SIGNAL(mediaInserted(int,int)), this, SLOT(endInsertItems()));
        disconnect(mPlaylist, SIGNAL(mediaAboutToBeRemoved(int,int)), this, SLOT(beginRemoveItems(int,int)));
        disconnect(mPlaylist, SIGNAL(mediaRemoved(int,int)), this, SLOT(endRemoveItems()));
        disconnect(mPlaylist, SIGNAL(mediaChanged(int,int)), this, SLOT(changeItems(int,int)));
    }

    beginResetModel();
    mPlaylist = playlist;

    if (mPlaylist) {
        connect(mPlaylist, SIGNAL(mediaAboutToBeInserted(int,int)), this, SLOT(beginInsertItems(int,int)));
        connect(mPlaylist, SIGNAL(mediaInserted(int,int)), this, SLOT(endInsertItems()));
        connect(mPlaylist, SIGNAL(mediaAboutToBeRemoved(int,int)), this, SLOT(beginRemoveItems(int,int)));
        connect(mPlaylist, SIGNAL(mediaRemoved(int,int)), this, SLOT(endRemoveItems()));
        connect(mPlaylist, SIGNAL(mediaChanged(int,int)), this, SLOT(changeItems(int,int)));
    }
    endResetModel();
}
bool PlaylistModel::setData(const QModelIndex& index, const QVariant& value, int role){
    Q_UNUSED(role);
    mData[index] = value;
    emit dataChanged(index, index);
    return true;
}
void PlaylistModel::beginInsertItems(int start, int end){
    mData.clear();
    beginInsertRows(QModelIndex(), start, end);
}
void PlaylistModel::endInsertItems(){
    endInsertRows();
}
void PlaylistModel::beginRemoveItems(int start, int end){
    mData.clear();
    beginRemoveRows(QModelIndex(), start, end);
}
void PlaylistModel::endRemoveItems(){
    endInsertRows();
}
void PlaylistModel::changeItems(int start, int end){
    mData.clear();
    emit dataChanged(index(start,0), index(end,ColumnCount));
}
