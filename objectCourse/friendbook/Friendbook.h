//
// Created by freak on 2015-11-04.
//

#ifndef FRIENDBOOK_FRIENDBOOK_H
#define FRIENDBOOK_FRIENDBOOK_H
#include <iostream>
#include <string>
#include "Friend.h"
#include <fstream>
using namespace std;


class FriendBook
{
private:
    int sizeofarr ;
    int year;
    string owner;
    int nrOfFriends;
    Friend ** friends;
public:
    FriendBook();
    FriendBook(FriendBook const& other);
    ~FriendBook();
    FriendBook(string owner, int year);
    FriendBook(string owner, int year, int sizeofarr);
    bool addFriend(string name,int birtDate);
     string show() const;
     bool removeFriend(string name , int birthdate); // parameters name, birthdate, ...
     int existFriend(string name, int birthdate) const; // parameters name, birthdate, ...
     bool showFriendsBornByYear(int year) const;
     int getNrOfFriends() const;
     string getOwner() const;
     void setOwner(string owner);
     int  getYear() const;
    void setYear(int year);
    void clear();
    void getFriendsAsString(string * arr, int n);
    void saveOnFile(string filename) const; // filename name of owner followed by .txt
    void readFromFile(string filename); // filename name of owner followed by .txt

    // following member functions require <operator for Friend
    void showFriendsSorted(); // do not change the internal order of the Friends
    void getFriendsAsStringsSorted(string arr[], int n); // // do not change the internal order of the Friends
};





#endif //FRIENDBOOK_FRIENDBOOK_H
