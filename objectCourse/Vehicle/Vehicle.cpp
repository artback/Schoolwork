//
// Created by freak on 2015-11-18.
//

#include "Vehicle.h"

Vehicle::Vehicle(string manufacturer) {
    this->manufacturer=manufacturer;
}
Vehicle::Vehicle() {

}
Vehicle::Vehicle(istream & in, ostream &out) {
    string brand;
    out << "brand?\n";
    in >> brand;
}
Vehicle::Vehicle(Vehicle const & other) {
this->manufacturer = other.manufacturer;
}
string Vehicle::toString()const {
   return (this->manufacturer);
}
Vehicle* Vehicle::clone(){
    return new Vehicle(*this);
}
