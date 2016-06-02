#include "keyboardshortcuts.h"


KeyboardShortcuts::KeyboardShortcuts(QWidget * parent): QWidget(parent)
{

      playKey= new QAction(this);
      playKey->setShortcut(Qt::Key_Space);
      connect(playKey,SIGNAL(triggered()),this,SIGNAL(play()));
      stopKey = new QAction(this);
      stopKey->setShortcut(Qt::Key_S);
      connect(stopKey,SIGNAL(triggered()),this,SIGNAL(stop()));
      forwardKey= new QAction(this);
      forwardKey->setShortcut(Qt::Key_Right);
      connect(forwardKey,SIGNAL(triggered()),this,SIGNAL(forward()));
      backKey= new QAction(this);
      backKey->setShortcut(Qt::Key_Left);
      connect(backKey,SIGNAL(triggered()),this,SIGNAL(back()));
      this->addAction(playKey);
      this->addAction(stopKey);
      this->addAction(forwardKey);
      this->addAction(backKey);

}
KeyboardShortcuts::~KeyboardShortcuts(){

}
