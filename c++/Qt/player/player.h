#ifndef PLAYER_H
#define PLAYER_H
#include <QWidget>
#include <QMediaPlayer>
#include <QMediaPlaylist>

QT_BEGIN_NAMESPACE
class QAbstractItemView;
class QLabel;
class QMediaPlayer;
class QModelIndex;
class QPushButton;
class QSlider;
class QVideoProbe;
class QVideoWidget;
QT_END_NAMESPACE

class PlaylistModel;
class HistogramWidget;

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

QMediaPlayer *player;
QMediaPlaylist *playlist;
QLabel *coverLabel;
QSlider *slider;
QLabel *labelDuration;
QPushButton *fullScreenButton;

QLabel *labelHistogram;

PlaylistModel *playlistModel;
QAbstractItemView *playlistView;
QString trackInfo;
QString statusInfo;
qint64 duration;
};

#endif // PLAYER_H
