QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4):
TARGET = musicplayer
TEMPLATE = app

QT += network \
      xml \
      multimedia \
      multimediawidgets \
      widgets

SOURCES += main.cpp\
    playercontrols.cpp \
    player.cpp \
    playlistmodel.cpp \
    keyboardshortcuts.cpp

HEADERS  += \
    playercontrols.h \
    player.h \
    playlistmodel.h \
    keyboardshortcuts.h

FORMS    +=
