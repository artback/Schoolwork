//
// Created by freak on 12/16/15.
//

#include "Media.h"

Media::Media() {
    titel="";
    pubYear=0;
}
Media::~Media() {

}
Media::Media(string titel, int pubYear) {
    this->titel=titel;
    this->pubYear=pubYear;
}
string Media::toString()const {
    return(titel + to_string(pubYear));
}
void Media::setTitel(string titel) {
    this->titel=titel;
}

