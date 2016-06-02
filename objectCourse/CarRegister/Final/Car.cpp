//
// Created by freak on 2015-11-16.
//

#include <fstream>
#include "Car.h"
Car::Car(){
this->brand="";
this->regNumber="";
this->miles=0;
this->price=0;
}
Car::Car(string regNumber){
    this->brand="";
    this->regNumber=regNumber;
    this->miles=0;
    this->price=0;
}
Car::~Car(){
}
Car::Car(string brand, string regNumber, int miles, int price) {
    this->brand=brand;
    this->regNumber=regNumber;
    this->miles=miles;
    this->price=price;
}
string Car::getbrand()const{
       return this->brand;
    }
string Car::getRegNumber()const{
        return this->regNumber;
}

int Car::getMiles()const{
        return this->miles;
}
int Car::getPrice()const{
        return this->price;
}

void Car::setBrand(string brand){
        this->brand=brand;
}

void Car::setRegNumber(string regNumber){
        this->regNumber=regNumber;
    }
void Car::setMiles(int miles){
        this->miles=miles;
    }
void Car::setPrice(int price){
        this->price = price;
    }
void Car::setPercentage(double percentage){
   this->price *= percentage;
}
bool Car::operator==(Car const & other)const{
    return (this->regNumber==other.regNumber);
}
string Car::toString()const{
    return (brand + " " + regNumber + " " + to_string(miles) + " " + to_string(price));
}
ifstream& Car::fromStream(ifstream& tos) {
        tos >> this->brand >> regNumber >> miles >> price;
        return tos;
}
ostream& operator<<(ostream& os , const Car & cr){
    os << cr.brand << " " << cr.regNumber << " " << cr.miles << " " << cr.price;
   return os;
}
istream& operator>>(istream& is ,  Car & cr) {
    is >> cr.brand >> cr.regNumber >> cr.miles >> cr.price;
    return is ;
}

