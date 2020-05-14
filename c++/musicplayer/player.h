#ifndef PLAYER_H
#define PLAYER_H
#include <QWidget>
#include <QMediaPlayer>
#include <QMediaPlaylist>
#include <QFileDialog>
#include "keyboardshortcuts.h"
#include "playercontrols.h"
#include <QMediaService>
#include <QMediaContent>
#include <QMediaMetaData>
#include <QtWidgets>

QT_BEGIN_NAMESPACE
class QAbstractItemView;
class QLabel;
class QMediaPlayer;
class QModelIndex;
class QPushButton;
class QSlider;
QT_END_NAMESPACE

class PlaylistModel;

class Player : public QWidget
{
    Q_OBJECT

public:
    Player(QWidget *parent = 0);
    ~Player();
private slots:
void open();
void durationChanged(qint64 duration);
void positionChanged(qint64 progress);
void metaDataChanged();
void previousClicked();
void shuffleClicked();
void seek(int seconds);
void jump(const QModelIndex &index);
void playlistPositionChanged(int);
void statusChanged(QMediaPlayer::MediaStatus status);
void displayErrorMessage();
void addToPlaylist(const QStringList &fileNames);
private:
void setTrackInfo(const QString &info);
void setStatusInfo(const QString &info);
void handleCursor(QMediaPlayer::MediaStatus status);
void updateDurationInfo(qint64 currentInfo);


QFileDialog* openFiles;
QMediaPlayer* player;
QMediaPlaylist* playlist;
QSlider* slider;
QLabel* labelDuration;

PlaylistModel* playlistModel;
PlayerControls* controls ;
KeyboardShortcuts* keys ;
QAbstractItemView* playlistView;
QString trackInfo;
QString statusInfo;
qint64 duration;
};
#endif // PLAYER_H
