//
// Created by freak on 11/28/15.
//

#ifndef TIMBER_WAINSCOTING_H
#define TIMBER_WAINSCOTING_H

#include <iostream>
#include "Timber.h"

using namespace std;

class WainScoting: public Timber {
public:
    WainScoting();
    WainScoting(int height, int width,int price,string profile,char painted);
    void setPaint(char painted);
    char getPaint()const;
    void setProfile(string profile);
    string getProfile()const;
    string toString()const;
private:
  string profile;
  char painted;
};


#endif //TIMBER_WAINSCOTING_H
