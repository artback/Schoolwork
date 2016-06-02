#ifndef PLAYERCONTROLS_H
#define PLAYERCONTROLS_H

#include <QMediaPlayer>
#include <QWidget>
#include <QBoxLayout>
#include <QSlider>
#include <QStyle>
#include <QToolButton>
#include <QComboBox>

QT_BEGIN_NAMESPACE
class QAbstractButton;
class QAbstractSlider;
class QComboBox;
QT_END_NAMESPACE

class PlayerControls : public QWidget
{
    Q_OBJECT

public:
    PlayerControls(QWidget *parent = 0);
    QMediaPlayer::State state() const;
    int volume() const;
public slots:
    void setState(QMediaPlayer::State state);
    void setVolume(int volume);
signals:
    void play();
    void pause();
    void stop();
    void next();
    void previous();
    void shuffle();
    void changeVolume(int volume);
public slots:
    void playClicked();
private:
    QMediaPlayer::State playerState;
    QAbstractButton *playButton;
    QAbstractButton *stopButton;
    QAbstractButton *nextButton;
    QAbstractButton *previousButton;
    QAbstractButton *shuffleButton;
    QAbstractSlider *volumeSlider;
};

#endif // PLAYERCONTROLS_H
