#include <iostream>
#include <fstream>
#include "Carregister.h"

using namespace std;
const int SIZEOFARR = 1;
void showInRange(Carregister & cars);
int menu();
void changePrice(Carregister & cars);
void printArrOfStrings(Carregister & cars);
void registerCar(Carregister & cars );
void removecar(Carregister & myCars);
void saveToFile(Carregister & cars);
void readFromFile(Carregister & cars);
int main() {
  Carregister myCars(SIZEOFARR);
    bool loop = true;
     readFromFile(myCars);
    do {
        int menuchoise = menu();
        switch(menuchoise) {
            case 0: {
                loop = false;
                break;
            }
            case 1: {
                registerCar(myCars);
                break;
            }
            case 2: {
                removecar(myCars);
                break;
            }
            case 3:{
             printArrOfStrings(myCars);
                break;
            }
            case 4:{
                showInRange(myCars);
                break;
            }
            case 5:{
                changePrice(myCars);
            }
            default:{
              break;
            }
        }
    }while(loop);
    saveToFile(myCars);
    return 0;
}
int menu(){
    int menuChoise = 0;

    cout << "\n\n0.Quit and save to file\n"
            "1.Register car\n"
            "2.Remove car\n"
            "3.Print all cars\n"
            "4.Show cars in range\n"
            "5.Change price in percentage \n";
    cin >> menuChoise;
    return menuChoise;
}
void removecar(Carregister & myCars){
    string regNumber;
    cout << "Licenses number?\n";
    cin.ignore();
    getline(cin,regNumber);
    if(myCars.removeCar(regNumber) >= 0){
        cout << "\nthe car was removed\n";
    }
    else{
        cout << "\nno car with that License number exist\n";
    }

}
void registerCar(Carregister & cars ) {
    string brand = "", regNumber = "";
    int price = 0, miles = 0;
    cout << "\nbrand? ";
    cin.ignore();
    getline(cin, brand);
    cout << "\nlicense Number? ";
    cin >> regNumber;
    cout << "\nmiles? ";
    cin >> miles;
    cout << "\nprice? ";
    cin >> price;
    bool exist = cars.addCar(brand, regNumber, miles, price);
    if (exist) {
        cout << "A car with that license number already exist\n";
    }
}
void changePrice(Carregister & cars){
    int lowprice = 0 , percentage  = 0;
    cout << "price roof?\n";
    cin >> lowprice;
    cout << "percentage? e.x 15 or -15\n";
    cin >> percentage;
  cars.changePrice(lowprice,percentage);
}
void showInRange(Carregister & cars) {
     int highprice = 0, lowprice = 0;
    int highMile = 0, lowMile = 0;
    cout << "High Price?\n";
    cin >> highprice;
    cout << "Low Price\n";
    cin >> lowprice;
    cout << "High Mile?\n";
    cin >> highMile;
    cout << "Low Mile?\n";
    cin >> lowMile;
    cout << cars.getCarsinRange(highprice,lowprice,highMile,lowMile);

}
void printArrOfStrings(Carregister & cars){
    int sizeOfstrings = cars.getNrOfCars();
    string * carsString = new string[sizeOfstrings];
    cars.getArrOfStrings(carsString);
    for (int i = 0; i < sizeOfstrings; ++i) {
        cout << carsString[i] << endl;
    }
    cout << endl << endl;
}
void saveToFile(Carregister & cars){
    string filename;
    cout << "filename?";
    cin >> filename;
    ofstream out;
    out.open(filename);
    if(!out){
        cout << "could not open file";
    }
    else {
        cars.saveToFile(out);
    }
}
void readFromFile(Carregister & cars){
    string filename = "";
    cout << "filename?";
    cin >> filename;
    ifstream in;
    in.open(filename);
    if(!in){
        cout << "could not open file";
    }
    else {
        cars.readFromFile(in);
    }
}
