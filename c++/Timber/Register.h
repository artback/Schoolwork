//
// Created by freak on 12/1/15.
//

#ifndef TIMBER_REGISTER_H
#define TIMBER_REGISTER_H

#include "Timber.h"
#include "WainScoting.h"
#include "RuleTimber.h"

class Register {
public:
    Register();
    Register(Register const & other);
    ~Register();
    Register& operator=(Register const& other);
    void addWain(int height,int width,int price,string profile, char painted) ;
    void addRuleT(int height,int width,int price, string classified);
    void allToString(string * arr)const;
    void wainToString(string * arr)const;
    void ruleTtostring(string * arr)const;
    int nrsOfTimber()const;
    int nrsOfRuleTimber()const;
    int nrsOfWainScoting()const;
    void remove(int height, int width ,int price );
    void changePainted(int index, char painted);
    int findWain(int height, int width, string profile)const;
private:
    void expand();
    int sizeofArr;
    int nrOfTimber;
    int nrOfWainscoting;
    int nrOfRuleTimber;
    Timber ** timber;

};


#endif //TIMBER_REGISTER_H
