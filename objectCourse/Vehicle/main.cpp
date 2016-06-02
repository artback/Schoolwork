#include <iostream>

#include "Car.h"
#include "Truck.h"
using namespace std;
int main() {
    Car* car1 = new Car("volvo",5);
    cout << car1->toString() << endl;
    Car* car2= car1->clone();
    delete car1;
    cout << car2->toString() << endl;
    Vehicle * vehicle[5];
    vehicle[0] = new Car(cin,cout);
    return 0;
}