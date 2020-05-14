//
// Created by freak on 2015-11-18.
//

#ifndef SHAPE_CIRCLE_H
#define SHAPE_CIRCLE_H

#include "Shape.h"
#include <iostream>

using namespace std;

class Circle: public Shape{
private:
  int radie;
public:
  Circle();
  Circle(int x, int y);
  int getAround();
  int getArea();
  int getRadie()const;
  void setRadie(int radie);
  string tostring();
};


#endif //SHAPE_CIRCLE_H
