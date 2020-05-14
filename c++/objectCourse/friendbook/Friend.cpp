//
// Created by freak on 2015-11-04.
//

#include "Friend.h"

Friend::Friend(){

}
Friend::Friend(string name, int birthDate)
{  if(birthDate == trueDate(birthDate)) {
        this->name = name;
        this->birthDate = birthDate;
    }
}
void Friend::readFromFile(ifstream & in)
{
    in >> this->name; in >> this->birthDate; in >> this->adress;
}
void Friend::setname(string name)
{
    this->name=name;
}

string Friend::getname() const{
    return this->name;
}

bool Friend::trueDate(int birthDate){
int year;
bool truedate = false;
year = birthDate/10000; int month;
month = (birthDate-(year*10000)/100); int day;
day = (birthDate-(year*10000)-(month*100));
if(year < 2015 && year > 1900 && month > 1 && month < 12 && day > 1  && day < 31 ) {
    this->birthDate = birthDate;
    truedate = true;
}
    return truedate;
}
bool Friend::setBD(int birthDate) {
    int year;
    bool truedate = false;
    year = birthDate/10000; int month;
    month = (birthDate-(year*10000)/100); int day;
    day = (birthDate-(year*10000)-(month*100));
    if(year < 2015 && year > 1900 && month > 1 && month < 12 && day > 1  && day < 31 )
    {
        this->birthDate = birthDate;
        truedate = true;
    }
    return truedate;
}
int Friend::getAge(int year)const{
    return year-birthDate/1000;
}
int Friend::getBD()const{
    return this->birthDate;
}
void Friend::setAdress(string adress) {
    this->adress=adress;

}
string Friend::getAdress()const{
    return this-> adress;
}
bool Friend::operator==(Friend const &other) const {
    return (this->name==other.name && this->adress==other.adress);
}
string Friend::tostring() const {
   return name + " " + to_string(birthDate) + " " + " " + adress;
}