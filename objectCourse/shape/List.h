//
// Created by freak on 12/2/15.
//

#ifndef SHAPE_LIST_H
#define SHAPE_LIST_H

#include <iostream>

template <typename T>
class List {
private:
T* items;
int capacity;
int nrOfItems;
int increment;
public:
    List();
    ~List();
    List(int cap,int inc);
    void add(T item);
    T& at(int pos)const;
    List<T>operator+(List<T> const& other);
};
template <typename T>
List<T>::List() {
    this->capacity=10;
    this->increment=2;
    this->nrOfItems=0;
    this->items = new T[capacity];
}
template <typename T>
List<T>::~List() {
    delete[] items;
    items = nullptr;
}
template <typename T>
List<T>::List(int cap, int inc) {
    this->capacity=cap;
    this->increment=inc;
    this->nrOfItems=0;
}
template <typename T>
void List<T>::add(T item){
    items[nrOfItems] = item;
    nrOfItems++;
}
template <typename T>
T& List<T>::at(int pos) const {
    return items[pos];
}
template <typename T>
List<T> List<T>::operator+(List<T>const & other) {
    T temp = new T[other.capacity +this->capacity];
    for (int i = 0; i < this->nrOfItems; ++i) {
        temp[i] = other.items[i];
    }
    for (int j = nrOfItems; j < other.nrOfItems; ++j) {
        temp[j] = other.items;
    }
    for (int k = other.nrOfItems+nrOfItems; k < (other.capacity+this->capacity); ++k) {
        temp[k] = nullptr;
    }
    delete[] items;
    items=temp;
    temp = nullptr;
}
#endif //SHAPE_LIST_H
