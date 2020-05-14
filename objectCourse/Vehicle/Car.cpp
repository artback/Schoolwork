//
// Created by freak on 2015-11-18.
//

#include "Car.h"
#include "fstream"
Car::Car(){
    this->maxload=0;
}
Car::Car(istream& in, ostream &out) : Vehicle(in,out){
    int maxload;
    out << "maxload?\n";
    in >> maxload;
}
Car::Car(string manu , int maxload ): Vehicle(manu){
    this->maxload=maxload;
}
string Car::toString()const {
  return (vec.toString() + " " + to_string(maxload));
}
Car* Car::clone() {
    return new Car(*this);
}



