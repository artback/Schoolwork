//
// Created by freak on 12/16/15.
//

#ifndef EXTENTA_NEWSPAPER_H
#define EXTENTA_NEWSPAPER_H

#include <iostream>
#include "Media.h"
using namespace std;

class NewsPaper : public Media {
public:
  NewsPaper();
  string toString()const;
  void setTitle(string title);
private:
   int lopNr;
   int nrOfexSk;
};


#endif //EXTENTA_NEWSPAPER_H
