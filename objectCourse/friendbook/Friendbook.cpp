//
// Created by freak on 2015-11-04.
//

#include "Friendbook.h"
#include "utilities.h"
FriendBook::FriendBook() {
    this->owner="";
    this->year=0;
    this->sizeofarr=10;
    friends = new Friend*[sizeofarr];
    for (int i = 0; i < sizeofarr; ++i) {
        friends[i]= nullptr;
    }
}
FriendBook::~FriendBook() {

}

FriendBook::FriendBook(FriendBook const& other){

    this->owner=other.owner;
    this->year=other.year;
    this->sizeofarr=other.sizeofarr;
    this->nrOfFriends=other.nrOfFriends;
    this->friends[other.sizeofarr];
    for (int i = 0; i < sizeofarr ; ++i) {
        if(friends[i] != nullptr){
            friends[i] == other.friends[i];
        }
    }
}
FriendBook::FriendBook(string owner, int year, int sizeofarr ) {
this->owner=owner;
this->year=year;
this->sizeofarr=sizeofarr;
this->nrOfFriends=0;
this->friends[sizeofarr];
}
FriendBook::FriendBook(string owner, int year ) {
    this->owner=owner;
    this->year=year;
    this->sizeofarr=10;
    this->nrOfFriends=0;
    friends = new Friend*[sizeofarr];
    for (int i = 0; i < sizeofarr; ++i) {
        friends[i] = nullptr;
    }
    }
bool FriendBook::addFriend(string name , int birthDate){
    bool added = false;
    int index = existFriend(name,birthDate);
    if(index > 0)
    {
        if(this->nrOfFriends==sizeofarr){
           expand(this->friends,sizeofarr);
        }
        *friends[nrOfFriends] = Friend (name, birthDate);
    }
    nrOfFriends++;
    return added;
}
string FriendBook::show() const{
    string s;
    for (int i = 0; i < nrOfFriends; ++i) {
        s += friends[i]->tostring() + " ";
    }
    return s;

}
bool FriendBook::removeFriend(string name,int birthDate){
    int index = existFriend(name,birthDate);
    bool removed = false;
    if (index >= 0) {
        removed = true;
        for (int i = index; i < nrOfFriends-1; ++i) {
            *friends[i]=*friends[i+1];
        }
        this->friends[nrOfFriends]= nullptr;
        nrOfFriends--;
    } return removed;
}
int FriendBook::existFriend(string name , int BirthDate) const{
    int exist = -1;
    Friend test(name,BirthDate);
    bool found = false;
        for (int i = 0; i < nrOfFriends & !found; ++i) {
            if(*friends[i] == test){
            exist = i;
            found = true;
        }
    }
    return exist;
} // parameters name, birthdate, ...
bool FriendBook::showFriendsBornByYear(int year) const{
    bool found = false;
    for (int i = 0; i < nrOfFriends; ++i) {
        if (friends[i]->getAge(year)== 0){
            cout << friends[i]->tostring();
            found = true;
        }
    }
    return found;
}
int FriendBook::getNrOfFriends() const{
    return nrOfFriends;
}
string FriendBook::getOwner() const{
    return owner;
}
void FriendBook::setOwner(string owner){
    this->owner=owner;
}
int FriendBook::getYear() const {
    return year;
}
void FriendBook::setYear(int year){
    this->year=year;
}
void FriendBook::clear(){
    nrOfFriends=0;
}
void FriendBook::getFriendsAsString(string * arr,int n){
    for (int i = 0; i < n; ++i) {
        arr[i] = friends[i]->tostring();
    }
}
void FriendBook::saveOnFile(string filename) const{
    ofstream out;
    out.open(filename);
    for (int i = 0; i < nrOfFriends; ++i) {
        out << friends[i]->getname() << endl;
        out << friends[i]->getBD() << endl;
        out << friends[i]->getAdress() << endl;
    }
} // filename name of owner followed by .txt
void FriendBook::readFromFile(string filename){
    ifstream in;
    string name,adress;
    int birthDate;
    in.open(filename);
    in >> nrOfFriends;
    for (int i = 0; i < nrOfFriends; ++i) {
        friends[i]->readFromFile(in);
    }
}

