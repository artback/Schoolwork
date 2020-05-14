#ifndef KEYBOARDSHORTCUTS_H
#define KEYBOARDSHORTCUTS_H

#include <QAction>
#include <QObject>
#include <QShortcut>
class KeyboardShortcuts : public QWidget
{
    Q_OBJECT
public:
    KeyboardShortcuts(QWidget * parent = 0);
    ~KeyboardShortcuts();
signals:
   void play();
   void stop();
   void forward();
   void back();
private:
 QAction* playKey;
 QAction* stopKey;
 QAction* forwardKey;
 QAction* backKey;
};

#endif // KEYBOARDSHORTCUTS_H
