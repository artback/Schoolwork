//
// Created by freak on 12/16/15.
//

#include "NewsPaper.h"
NewsPaper::NewsPaper(): Media() {
 lopNr=0;
  nrOfexSk=0;
}
string NewsPaper::toString() const{
   return (this->Media::toString() +
           (to_string(lopNr) + to_string(nrOfexSk)));
}


