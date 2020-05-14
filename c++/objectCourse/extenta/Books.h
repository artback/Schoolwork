//
// Created by freak on 12/16/15.
//

#ifndef EXTENTA_BOOKS_H
#define EXTENTA_BOOKS_H

#include "Media.h"

class Books: public Media {
public:
 Books();
 void setAuthor(string author);
 string toString()const;
private:
 string  author;
 };


#endif //EXTENTA_BOOKS_H
