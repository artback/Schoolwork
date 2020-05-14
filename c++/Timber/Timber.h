//
// Created by freak on 11/28/15.
//

#ifndef TIMBER_TIMBER_H
#define TIMBER_TIMBER_H

#include <iostream>

using namespace std;

class Timber {

public:
    Timber();
    Timber(Timber const & other);
    virtual ~Timber() = 0;
    Timber(int height,int width,int price);
    string getDimension()const;
    void setDimension(int height,int length );
    int getPrice()const;
    void setPrice(int price) ;
    virtual string toString()const;
private:
    int height;
    int width;
    int price;
};


#endif //TIMBER_TIMBER_H
