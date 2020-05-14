//
// Created by freak on 11/28/15.
//

#include "RuleTimber.h"


RuleTimber::RuleTimber():Timber() {
    this->classified="";
}
RuleTimber::RuleTimber(int height, int width, int price, string classified):Timber(height,width,price) {
  setClassified(classified);
}
string RuleTimber::toString()const {
    return( this->Timber::toString()+ "class: " + classified);
}
string RuleTimber::getClassified() const{
    return classified;
}
void RuleTimber::setClassified(string classified) {
    this->classified=classified;
}

