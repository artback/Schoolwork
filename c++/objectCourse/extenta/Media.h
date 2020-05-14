//
// Created by freak on 12/16/15.
//

#ifndef EXTENTA_MEDIA_H
#define EXTENTA_MEDIA_H

#include <iostream>
#include <string>

using namespace std;


class Media {


public:
    Media();
    virtual ~Media() = 0;
    Media(string titel,int pubYear);
    virtual void setTitel(string titel);
    virtual string toString()const ;
private:
    int pubYear;
    string titel;
};


#endif //EXTENTA_MEDIA_H
