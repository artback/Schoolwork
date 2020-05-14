//
// Created by freak on 2015-11-16.
//

#include "Carregister.h"

Carregister::Carregister() {
    this->sizeOfArr=10;
    this->nrOfCars=0;
    this->cars = new Car*[sizeOfArr];
    for (int i = 0; i < sizeOfArr; ++i) {
        cars[i] = nullptr;
    }
}
Carregister::Carregister(int sizeOfArr) {
    this->sizeOfArr=sizeOfArr;
    this->nrOfCars=0;
    this->cars = new Car*[sizeOfArr];
    for (int i = 0; i < sizeOfArr; ++i) {
        cars[i] = nullptr;
    }
}
Carregister::~Carregister() {
    for (int i = 0; i < nrOfCars; ++i) {
        delete cars[i];
    }
    delete[] cars;
    cars = nullptr;
}
Carregister::Carregister(Carregister const & other ) {
   this->sizeOfArr=other.sizeOfArr;
   this->nrOfCars=other.nrOfCars;
   this->cars=new Car*[sizeOfArr];
    for (int i = 0; i < sizeOfArr; ++i) {
        cars[i] = nullptr;
    }
    for (int j = 0; j < nrOfCars; ++j)
    {
        if(nrOfCars==sizeOfArr)
        {
            expand();
        }
       this->cars[j]=new Car(*other.cars[j]);
    }


}
bool Carregister::addCar(string brand,string regnumber,int miles,int price) {
   bool existed = true;
   if(exist(regnumber) == -1)
   {
       if(sizeOfArr==nrOfCars){
           expand();
       }
       existed = false;
       cars[nrOfCars] = new Car(brand,regnumber,miles,price);
       nrOfCars++;
   }
    return existed;
}
int Carregister::removeCar(string regnumber) {
    int index = exist(regnumber);
    if (index >= 0)
    {
        for (int i = index; i < nrOfCars-1; ++i) {
            cars[i]=cars[i+1];
        }
        nrOfCars--;
    }
    return index;
}
int Carregister::exist(string regNumber) {
    int index = -1;
    bool existed = false;
    for (int i = 0; i < nrOfCars && !existed; ++i) {
        if(regNumber == cars[i]->getRegNumber()){
           existed=true;
            index = i;
        }
    }
    return index;
}
void Carregister::getArrOfStrings(string * arr)const{
    for (int i = 0; i < nrOfCars; ++i) {
        arr[i] = cars[i]->tostring();
    }
}
int Carregister::getNrOfCars()const {
    return (this->nrOfCars);
}
string Carregister::getCarsinRange(int highprice, int lowprice, int highMile, int lowMile) {
    string s = "";
    for (int i = 0; i < nrOfCars; ++i) {

    if (cars[i]->getPrice() < highprice && cars[i]->getPrice() > lowprice &&
                cars[i]->getMiles() < highMile && cars[i]->getMiles() > lowMile)
        {
            s += cars[i]->tostring()+ "\n";
        }
    }
    return s;
}
void Carregister::expand() {
    Car ** temp = new Car*[sizeOfArr*2];
    for (int i = 0; i < sizeOfArr; ++i) {
        temp[i] = new Car(*cars[i]);
    }
    delete[] cars;
    cars = temp;
    sizeOfArr *= 2;
}
void Carregister::changePrice(int lowprice , double percentage) const{
    percentage /= 100 ;
    percentage += 1;
    for (int i = 0; i < nrOfCars; ++i) {
        if (cars[i]->getPrice() > lowprice){
            cars[i]->setPercentage(percentage);
        }
    }
}
Carregister Carregister::operator=(Carregister const & other ){
    this->sizeOfArr=other.sizeOfArr;
    this->nrOfCars=other.nrOfCars;
    this->cars=new Car*[other.sizeOfArr];
    for (int k = 0; k < other.nrOfCars; ++k) {
        if(this->nrOfCars==this->sizeOfArr){
            expand();
        }
        cars[k] = new Car(*(other.cars[k]));
    }
    for (int j = nrOfCars; j < other.sizeOfArr; ++j) {
        cars[j] = nullptr;
    }
}
void Carregister::saveToFile(ofstream& out){
    out << nrOfCars << endl;
    out << sizeOfArr << endl;
    for (int i = 0; i < nrOfCars; ++i)
    {
        operator<<(out,*cars[i]);
        out << endl;
    }
}
void Carregister::readFromFile(ifstream& ifs) {
    ifs >> nrOfCars;
    ifs >> sizeOfArr;
    expand();
    for(int j = 0; j < nrOfCars; ++j)
    {
        cars[j] = new Car;
    }
    for (int i = 0; i < nrOfCars; ++i)
    {
        cars[i]->fromStream(ifs);
    }
}
