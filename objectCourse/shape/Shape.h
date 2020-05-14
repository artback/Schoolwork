//
// Created by freak on 2015-11-18.
//

#ifndef SHAPE_SHAPE_H
#define SHAPE_SHAPE_H

#include <iostream>
#include <string>

using namespace std;

class Shape {
private:
    int x;
    int y;
public:
    Shape();
    int getx()const ;
    int gety()const;
    void setX(int x);
    void setY(int y);
    virtual string toString()const;
};
ostream& operator<<(ostream& output, Shape const& other){
    return output << other.toString();
}

#endif //SHAPE_SHAPE_H
