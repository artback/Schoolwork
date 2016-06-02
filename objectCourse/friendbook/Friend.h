//
// Created by freak on 2015-11-04.
//

#ifndef FRIENDBOOK_FRIEND_H
#define FRIENDBOOK_FRIEND_H

#include <iostream>
#include <fstream>
using namespace std;
class Friend {
private :
    string name;
    int birthDate;
    string adress;
public:
    Friend();
    Friend(string name,int birthDate);
    Friend(string name,int birthDate,string adress);
    bool operator==(Friend const & other)const;
    void readFromFile(ifstream & in);
    bool trueDate(int birthDate);
    void setname(string name);
    string getname() const;
    bool setBD(int birthDate);
    int getBD()const;
    void setAdress(string adress);
    string getAdress()const;
    int getAge(int year)const;
    string tostring()const;

};


#endif //FRIENDBOOK_FRIEND_H
