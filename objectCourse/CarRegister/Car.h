//
// Created by freak on 2015-11-16.
//

#ifndef CARREGIGISTER_CAR_H
#define CARREGIGISTER_CAR_H
#include <iostream>
#include <string>
using namespace std;
class Car {
public:
    Car();
    Car(string brand,string regNumber, int miles,int price);
    Car(string regNumber);
    ~Car();
    string toString()const;
    bool operator==(Car const & other)const;
    friend ostream& operator<<(ostream& os , const Car & cr);
    string getbrand()const;
    string getRegNumber()const;
    int getMiles()const;
    int getPrice()const;

    void setPercentage(double percentage);
    void setBrand(string brand);
    void setRegNumber(string regNumber);
    void setMiles(int miles);
    void setPrice(int price);
    ifstream& fromStream(ifstream& tos);
    friend istream& operator>>(istream& is , Car & cr);
private:
string brand;
string regNumber;
int miles;
int price;
};


#endif //CARREGIGISTER_CAR_H
