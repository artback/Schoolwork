//
// Created by freak on 12/1/15.
//

#include "Register.h"
#include <typeinfo>

Register::Register(){
sizeofArr=3;
this->nrOfTimber=0;
this->nrOfWainscoting=0;
this->nrOfRuleTimber=0;
timber = new Timber*[sizeofArr];
    for (int i = 0; i <sizeofArr ; ++i) {
        timber[i]= nullptr;
    }
}
Register::Register(Register const &other) {
    this->sizeofArr=other.sizeofArr;
    this->nrOfTimber=other.nrOfTimber;
    this->nrOfWainscoting=other.nrOfWainscoting;
    this->nrOfRuleTimber=other.nrOfRuleTimber;
    timber = new Timber*[sizeofArr];
    for (int i = 0; i <sizeofArr ; ++i) {
        WainScoting *w = dynamic_cast<WainScoting*>(other.timber[i]);
        if (w != nullptr) {
            timber[i] = new WainScoting(*w);
        }
        else {
            RuleTimber *r = dynamic_cast<RuleTimber *>(other.timber[i]);
            if (r != nullptr) {
                timber[i] = new RuleTimber(*r);
            }
        }
    }
}
Register& Register::operator=(Register const &other) {
    this->sizeofArr = other.sizeofArr;
    this->nrOfTimber = other.nrOfTimber;
    this->nrOfWainscoting = other.nrOfWainscoting;
    this->nrOfRuleTimber = other.nrOfRuleTimber;
    for (int k = 0; k < nrOfRuleTimber; ++k) {
       delete timber[k];
    }
    delete[] timber;
    timber = NULL;

    timber = new Timber* [sizeofArr];
    for (int i = 0; i < sizeofArr; ++i) {
        WainScoting* w = dynamic_cast<WainScoting*>(other.timber[i]);
        if (w != nullptr) {
            timber[i] = new WainScoting(*w);
        }
        RuleTimber* r = dynamic_cast<RuleTimber*>(other.timber[i]);
        if (r != nullptr) {
            timber[i] = new RuleTimber(*r);
        }
    }
   return (*this);
}
Register::~Register() {
    for (int i = 0; i < nrOfTimber; ++i)
    {
        delete timber[i];
    }
    delete[] timber;
    timber=nullptr;
}
void Register::addRuleT(int height,int width,int price, string classified){
    if(nrOfTimber==sizeofArr)
    {
       expand();
    }
    timber[nrOfTimber]= new RuleTimber(height,width,price,classified);
    nrOfRuleTimber++;
    nrOfTimber++;
}
void Register::addWain(int height,int width,int price,string profile,char painted) {
    if(nrOfTimber==sizeofArr)
    {
        expand();
    }
    timber[nrOfTimber] = new WainScoting(height,width,price,profile,painted);
    nrOfWainscoting++;
    nrOfTimber++;
}
int Register::nrsOfRuleTimber()const {
    return nrOfRuleTimber;
}
int Register::nrsOfTimber()const {
    return nrOfTimber;
}
int Register::nrsOfWainScoting() const{
    return nrOfWainscoting;
}
void Register::expand(){
    Timber **temp = new Timber *[sizeofArr*2];
    for (int i = 0; i < sizeofArr; ++i) {
        WainScoting* w = dynamic_cast<WainScoting*>(timber[i]);
        if(w != nullptr){
            temp[i] = new WainScoting(*w);
        }
        else{
        RuleTimber* r = dynamic_cast<RuleTimber*>(timber[i]);
        if(r != nullptr){
          temp[i] = new RuleTimber(*r);
        }
        }
    }
    for (int i = 0; i < nrOfTimber; ++i){
        delete timber[i];
    }
    delete[] timber;
    timber = nullptr;
    timber = temp;
    sizeofArr *= 2;
}
void Register::allToString(string *arr)const {
    for (int i = 0; i < nrOfTimber; ++i) {
        WainScoting* w = dynamic_cast<WainScoting*>(timber[i]);
        if(w != nullptr){
            arr[i] = w->toString()+"\n";
        }
        else {
            RuleTimber* r = dynamic_cast<RuleTimber *>(timber[i]);
            if (r != nullptr) {
                arr[i] = r->toString() + "\n";
            }
        }
    }
}
void Register::wainToString(string *arr)const {
    int n = 0;
    for (int i = 0; i < nrOfTimber; ++i) {
          WainScoting* w = dynamic_cast<WainScoting*>(timber[i]);
           if(w != nullptr){
               arr[n] = w->toString() + "\n";
               n++;
           }
    }
}
void Register::ruleTtostring(string *arr)const {
    int n =0;
    for (int i = 0; i < nrOfTimber; ++i) {
        RuleTimber* r = dynamic_cast<RuleTimber*>(timber[i]);
        if(r != nullptr){
            arr[n] = r->toString();
        }
    }
}
void Register::remove(int height, int width ,int price){
    string dimensions=to_string(height)+"x"+to_string(width);
    int index = -1;
    bool found = false;
    for (int i=0; i < nrOfTimber && !found ; ++i) {
        if(timber[i]->getDimension()==dimensions && timber[i]->getPrice() == price) {
            index = i;
            found = true;
        }
    }
    if (index >= 0){
        for (int i = index; i <nrOfTimber-1 ; ++i) {
            timber[i]=timber[i+1];
        }
        delete timber[nrOfTimber];
        timber[nrOfTimber]=nullptr;
        nrOfTimber--;
    }
}
int Register::findWain(int height, int width, string profile)const {
    string dimensions = to_string(height) + "x" + to_string(width);
    int index = -1;
    bool found = false;
    for (int i = 0; i < nrOfTimber && !found; ++i) {
         WainScoting* w = dynamic_cast<WainScoting *>(timber[i]);
            if (w != nullptr) {
        if (dimensions == timber[i]->getDimension() && w->getProfile() == profile) {
                    found = true;
                    index = i;
            }
        }
        return index;
    }
}
void Register::changePainted(int index, char painted) {
    if (index >= 0 && index < nrOfTimber) {
        WainScoting *w = dynamic_cast<WainScoting *>(timber[index]);
        if (w != nullptr) {
            w->setPaint(painted);
        }
    }
}
