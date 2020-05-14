//
// Created by freak on 2015-11-18.
//

#ifndef VEHICLE_CAR_H
#define VEHICLE_CAR_H

#include "Vehicle.h"
#include <iostream>
using namespace std;

class Car: public Vehicle {

public:
 Car ();
 Car(istream&,ostream& out);
 Car(string manu, int pass);
 string toString()const;
 Car* clone();
private:
    int maxload;
    Vehicle vec;

};


#endif //VEHICLE_CAR_H
