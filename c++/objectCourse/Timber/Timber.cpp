//
// Created by freak on 11/28/15.
//

#include "Timber.h"

Timber::Timber() {
  height=0;
  width=0;
  price=0;
}
Timber::~Timber() {

}
Timber::Timber(int height,int width,int price) {
  this->height=height;
  this->width=width;
  this->price=price;
}
Timber::Timber(Timber const & other) {
  this->height=other.height;
  this->width=other.width;
  this->price=other.price;
}
int Timber::getPrice() const {
  return price;
}
void Timber::setPrice(int price) {
  this->price=price;
}
void Timber::setDimension(int height, int width) {
  this->height=height;
  this->width=width;
}
string Timber::getDimension() const {
  return (to_string(height)+"x"+to_string(width));
}

