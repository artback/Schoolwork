//
// Created by freak on 2015-11-18.
//

#include "Shape.h"
Shape::Shape() {
    this->setX(0);
    this->setY(0);
}

void Shape::setY(int y) {
    this->y=y;
}

void Shape::setX(int x) {
    this->x=x;
}
int Shape::getx() const {
  return x;
}
int Shape::gety()const{
   return y;
}
string Shape::toString()const {
    return (to_string(x)+" " + to_string(y));
}


