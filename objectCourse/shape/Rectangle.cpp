//
// Created by freak on 2015-11-18.
//

#include "Rectangle.h"


Rectangle::Rectangle():Shape() {
    this->height=0;
    this->length=0;
}
int Rectangle::getHeight() {
    return height;
}
int Rectangle::getLength() {
    return length;
}
int Rectangle::getArea() {
    return height*length;
}
int Rectangle::around() {
  return (2*height + 2*length);
}
string Rectangle::tostring() {
    return (Shape::toString() + " " + to_string(getArea()) + " " + to_string(around()));
}