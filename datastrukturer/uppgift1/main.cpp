// Created by freak on 2/3/16.
//

#include "Snake.h"
#include <iostream>

using namespace std;

int main(){

    Snake mSnake;

    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.setDirection(LEFT);
    vector<SnakeEntity> body = mSnake.getBody();
    for (int i = 0; i < body.size() ; ++i)
    {
        cout << body[i].getX() << body[i].getY() << endl;
    }
    cout<< endl << mSnake.getHead().getX() << mSnake.getHead().getY();

    mSnake.moveHead();
    mSnake.moveHead();
    cout << endl << mSnake.collidedWithSelf();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    cout<< endl << mSnake.getHead().getX() << "  " << mSnake.getHead().getY();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    mSnake.insertNodeAfterHead();
    cout << endl << mSnake.collidedWithSelf();
    mSnake.setDirection(RIGHT);
    mSnake.moveHead();
    mSnake.moveHead();
    mSnake.moveHead();
    cout<< endl << mSnake.getHead().getX() << "  " << mSnake.getHead().getY();
    mSnake.moveHead();
    cout<< endl << mSnake.getHead().getX() << "  " << mSnake.getHead().getY();



return 0;
}
