#include "Jumper.h"
#include <iostream>

using namespace std;
Jumper::Jumper()
{
    this->name = "";
    this->nr = 0;
    this->nrOfJumps = 0;
    this->nrOfMadeJumps = 0;
    this->results = nullptr;
}

Jumper::Jumper(Jumper const& other){

    this->name = other.name;
    this->nr = other.nr;
    this->nrOfJumps = other.nrOfJumps;
    this->nrOfMadeJumps = other.nrOfJumps;
    this->results = new int[other.nrOfJumps+1];
    for (int i = 0; i <= other.nrOfJumps; ++i) {
        this->results[i]= other.results[i];
    }
}
Jumper::Jumper(string name, int nr, int nrOfJumps)
{
    this->name = name;
    this->nr = nr;
    this->nrOfJumps = nrOfJumps;
    this->nrOfMadeJumps = 0;
    this->results = new int[this->nrOfJumps+1];
    for (int i=0; i<=this->nrOfJumps; i++)
    {
        this->results[i] = 0;
    }
}

Jumper Jumper::operator=(Jumper const& other){
    if(this != &other ){

        delete[] this->results;
        this->name = other.name;
        this->nr = other.nr;
        this->nrOfJumps = other.nrOfJumps;
        this->nrOfMadeJumps = other.nrOfJumps;
        this->results = new int[other.nrOfJumps+1];
        for (int i = 0; i < nrOfJumps; ++i) {

        }
    }
    return *this;
}


Jumper::~Jumper()
{
    delete[] results;
}
string Jumper::tostring() const{
    string s = to_string(this->nr) + ", " + name + ", ";
    for(int i = 0; i <= nrOfJumps ; i++){
        if(results[i] != 0){
            s += (to_string(results[i]) + ", ");
        }
    }
    s +="\n";
    return s ;
};
void Jumper::setName(string name)
{
    this->name = name;
}

void Jumper::setNr(int nr)
{
    this->nr = nr;
}
void Jumper::addResult(int jump, int length)
{
    this->results[jump] = length;
}

string Jumper::getName() const
{
    return this->name;
}

int Jumper::getNr()	const
{
    return this->nr;
}

int Jumper::getResult(int jump) const
{
    return this->results[jump];
}
