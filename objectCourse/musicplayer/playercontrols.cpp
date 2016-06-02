#include "playercontrols.h"

PlayerControls::PlayerControls(QWidget* parent) : QWidget(parent)
    , playerState(QMediaPlayer::StoppedState)
    , playButton(0)
    , stopButton(0)
    , nextButton(0)
    , previousButton(0)
    , shuffleButton(0)
    , volumeSlider(0){

    playButton = new QToolButton(this);
    playButton->setIcon(style()->standardIcon(QStyle::SP_MediaPlay));
    connect(playButton, SIGNAL(clicked()), this, SLOT(playClicked()));
    
    stopButton = new QToolButton(this);
    stopButton->setIcon(style()->standardIcon(QStyle::SP_MediaStop));
    stopButton->setEnabled(false);
    connect(stopButton, SIGNAL(clicked()), this, SIGNAL(stop()));

    nextButton = new QToolButton(this);
    nextButton->setIcon(style()->standardIcon(QStyle::SP_MediaSkipForward));
    connect(nextButton, SIGNAL(clicked()), this, SIGNAL(next()));

    previousButton = new QToolButton(this);
    previousButton->setIcon(style()->standardIcon(QStyle::SP_MediaSkipBackward));
    connect(previousButton, SIGNAL(clicked()), this, SIGNAL(previous()));

    QPixmap pix("/home/freak/Music/musicplayer/shuffle.png");
    shuffleButton = new QToolButton(this);
    shuffleButton->setIcon(pix);
    connect(shuffleButton,SIGNAL(clicked()),this , SIGNAL(shuffle()));

    volumeSlider = new QSlider(Qt::Horizontal, this);
    volumeSlider->setRange(0, 100);
    connect(volumeSlider, SIGNAL(sliderMoved(int)), this, SIGNAL(changeVolume(int)));

    QBoxLayout *layout = new QHBoxLayout;
    layout->setMargin(2);
    layout->addWidget(stopButton);
    layout->addWidget(previousButton);
    layout->addWidget(playButton);
    layout->addWidget(nextButton);
    layout->addWidget(shuffleButton);
    layout->addWidget(volumeSlider);
    setLayout(layout);
}

QMediaPlayer::State PlayerControls::state() const{
    return playerState;
}
void PlayerControls::setState(QMediaPlayer::State state){
    if (state != playerState) {
        playerState = state;

        switch (state) {
        case QMediaPlayer::StoppedState:
            stopButton->setEnabled(false);
            playButton->setIcon(style()->standardIcon(QStyle::SP_MediaPlay));
            break;
        case QMediaPlayer::PlayingState:
            stopButton->setEnabled(true);
            playButton->setIcon(style()->standardIcon(QStyle::SP_MediaPause));
            break;
        case QMediaPlayer::PausedState:
            stopButton->setEnabled(true);
            playButton->setIcon(style()->standardIcon(QStyle::SP_MediaPlay));
            break;
        }
    }
}
int PlayerControls::volume() const{
    return volumeSlider ? volumeSlider->value() : 0;
}
void PlayerControls::setVolume(int volume){
    if (volumeSlider)
        volumeSlider->setValue(volume);
}
void PlayerControls::playClicked()
{
    switch (playerState) {
    case QMediaPlayer::StoppedState:
    case QMediaPlayer::PausedState:
        emit play();
        break;
    case QMediaPlayer::PlayingState:
        emit pause();
        break;
    }
}
