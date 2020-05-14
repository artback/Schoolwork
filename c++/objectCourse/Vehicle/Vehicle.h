//
// Created by freak on 2015-11-18.
//

#ifndef VEHICLE_VEHICLE_H
#define VEHICLE_VEHICLE_H

#include <iostream>
#include <string>


using namespace std;

class Vehicle {
private:
    
        string manufacturer;
public:
    Vehicle();
    Vehicle(istream&,ostream& out);
    Vehicle(Vehicle const & other);
    Vehicle* clone();
    Vehicle(string manufacturer);
    virtual string toString()const;
};


#endif //VEHICLE_VEHICLE_H