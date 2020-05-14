//
// Created by freak on 2015-11-18.
//

#ifndef SHAPE_RECTANGLE_H
#define SHAPE_RECTANGLE_H

#include "Shape.h"

class Rectangle : public Shape{
public:
  Rectangle();
  int getHeight();
  int getLength();
  int getArea();
  int around();
  string tostring();
private:
    int height;
    int length;
};


#endif //SHAPE_RECTANGLE_H
