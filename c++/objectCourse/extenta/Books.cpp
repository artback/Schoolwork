//
// Created by freak on 12/16/15.
//

#include "Books.h"
Books::Books():Media(){
author="";
}
void Books::setAuthor(string author) {
    this->author=author;
}
string Books::toString()const {
    return (this->Media::toString()) + author;
}