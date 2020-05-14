//
// Created by freak on 11/28/15.
//

#ifndef TIMBER_RULETIMBER_H
#define TIMBER_RULETIMBER_H

#include "Timber.h"

class RuleTimber:public Timber {
public:
    RuleTimber();
    RuleTimber(int height, int width, int price, string classified);
    string toString()const;
    void setClassified(string classified);
    string getClassified()const;
private:
    string classified;
};


#endif //TIMBER_RULETIMBER_H
