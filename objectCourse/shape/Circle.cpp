//
// Created by freak on 2015-11-18.
//

#include "Circle.h"
#include <cmath>

Circle::Circle() {
 this->radie=0;

}
Circle::Circle(int x , int y) {
 this->setX(x);
 this->setY(y);
}
int Circle::getRadie() const {
return this->radie;
}
void Circle::setRadie(int radie) {
 this->radie=radie;
}
int Circle::getArea() {
return(pow(radie,2)*3.14);
}
int Circle::getAround() {
return (radie*radie*3.14);
}
string Circle::tostring() {
 return (Shape::toString() + " " + to_string(getArea()) + " " + to_string(getAround()));

}
