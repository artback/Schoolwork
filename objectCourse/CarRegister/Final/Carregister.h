//
// Created by freak on 2015-11-16.
//

#ifndef CARREGIGISTER_CARREGISTER_H
#define CARREGIGISTER_CARREGISTER_H

#include "Car.h"
#include <iostream>
#include <fstream>

class Carregister {
public:
Carregister();
Carregister(ifstream& ifs);
Carregister(int SizeOfArr);
~Carregister();
Carregister(Carregister const & other);
Carregister operator=(Carregister const & other );
bool addCar(string brand,string regnumber,int miles,int price);
int removeCar(string regnumber);
string getCarsinRange(int highprice , int lowprice,int highmile,int lowmile);
int getNrOfCars()const;
void getArrOfStrings(string * arr)const;
void changePrice(int lowprice , double percentage)const;
void saveToFile(ofstream& out);
void readFromFile(ifstream& ifs);
private:
  Car ** cars;
  int sizeOfArr;
  int nrOfCars;
  void expand();
  int exist(string regNumber);



};


#endif //CARREGIGISTER_CARREGISTER_H
