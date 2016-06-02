//
// Created by freak on 11/28/15.
//

#include "WainScoting.h"

WainScoting::WainScoting():Timber() {
    setProfile("");
    setPaint('n');
}
WainScoting::WainScoting(int height, int width,int price,string profile,char painted):Timber(height,width,price){
    setProfile(profile);
    setPaint(painted);
}
void WainScoting::setPaint(char painted) {
    this->painted=painted;
}
char WainScoting::getPaint()const {
    return painted;
}
string WainScoting::getProfile()const{
  return profile;
}
string WainScoting::toString()const {
    return(getDimension()+" " + to_string(getPrice()) + " painted: "+ painted);
}
void WainScoting::setProfile(string profile) {
    this->profile=profile;
}