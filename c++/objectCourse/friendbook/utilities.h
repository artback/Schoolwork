//
// Created by freak on 2015-11-06.
//
#include <fstream>
#include <iostream>
#include "Friendbook.h"

#ifndef FRIENDBOOK_UTILITIES_H
#define FRIENDBOOK_UTILITIES_H
using namespace std;
template <typename T>
void expand(T **& arr,int & size){
    T ** temp = new T*[size*2];
    for (int i = 0; i < size; ++i)
    {
       temp[i] = arr[i];
    }
    delete[] arr;
    arr = temp;
    size *= 2;
}
void readFromFile(FriendBook * arr);
void savetoFile(FriendBook * arr , int sizefiles);
int findBook(FriendBook **book, string owner , int size);
#endif //FRIENDBOOK_UTILITIES_H
