#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <iostream>
#include "Course.h"


using namespace std;
//linear probing hashtable

template <typename HashElement>
class HashTable
{
private:
	// internal datastructure
	int nrOfCollisions;
	int nrOfElements;
	int hashTableSize;
	HashElement** table;
	int myHash(const HashElement& elem) const {
		static Hash<HashElement> hashFunc;
		return hashFunc(elem) % hashTableSize; // hashFunc(elem) is a call of the defined operator() for HashElement
    }
public:
	HashTable(int hashTableSize = 101){
    this->hashTableSize=hashTableSize;
    this->nrOfCollisions=nrOfElements=0;
	this->table= new HashElement*[hashTableSize];
    for (int i = 0; i < hashTableSize; ++i) {
	    table[i] = nullptr;
    }
    }
	HashTable(const HashTable& aTable){
		this->hashTableSize = aTable.hashTableSize;
		this->table  = new HashElement*[hashTableSize];
		this->nrOfElements=aTable.nrOfElements;
		this->nrOfCollisions=aTable.nrOfCollisions;
		for (int j = 0; j < hashTableSize; ++j) {
			if(aTable.table[j]) {
				this->table[j] = new HashElement(*aTable.table[j]);
			}
			else{
				this->table[j]= nullptr;
			}
		}
	}
    virtual ~HashTable(){
		makeEmpty();
        delete[] table;
	}
	HashTable& operator=(const HashTable& aTable){
        makeEmpty();
        delete[] table;
		this->hashTableSize=aTable.hashTableSize;
		this->nrOfElements=aTable.nrOfElements;
		this->nrOfCollisions=aTable.nrOfCollisions;
		this->table = new HashElement[hashTableSize];
		for (int j = 0; j < hashTableSize; ++j) {
			if (aTable.table[j]) {
				this->table[j] = new HashElement(*aTable.table[j]);
			}
			else {
				this->table[j] = nullptr;
			}
		}
        return *(this);
	}
	int contains(const HashElement& elem) const {
	int index = myHash(elem);
	bool oneway = false;
	int startindex=index;
	if (*table[index] == elem)  { return index; }
	else {
		while(table[index] && !oneway){
			if (index == hashTableSize-1) { index=0; }
			if (*table[index] == elem) { return index; }
			index++;
			if(index==startindex){
				oneway=true;
				return -1;
			}
		}
	}
		return -1;
    }
	bool insert(const HashElement& elem){
		int index = myHash(elem);
		bool inserted = false;
        if(table[index] == nullptr) {
           table[index] = new HashElement(elem);
	        inserted=true;
           nrOfElements++;
	      }
		else{
	        while(table[index] != nullptr){
		        if (index == hashTableSize-1) {
			        index=0;
		        }
		        index ++;
	        }
	        if(index > -1) {
            table[index] = new HashElement(elem);
	        inserted=true;
            nrOfElements++;
		    nrOfCollisions++;
            }
         }
		return inserted;
	}
	bool remove(const HashElement& elem) {
		int index = myHash(elem);
		if (!table[index]) {
			return false;
		}
		else if(*table[index] == elem ) {
			delete table[index];
			table[index] = nullptr;

			while (table[index + 1]) {
				HashElement temp = *table[index + 1];
				delete table[index + 1];
				table[index + 1] = nullptr;
				insert(temp);
				index++;
			}
		}

		return true;
	}
	HashElement& get(int index) const{
		return *table[index];
	}
	void makeEmpty() {
		for (int i = 0; i < hashTableSize; ++i) {
			if (table[i] != nullptr) {
				delete table[i];
				table[i] = nullptr;
			}
		}
		nrOfElements=0;
		resetNrOfCollisions();
	}

	float loadFactor() const{
    return (double)nrOfElements/(double)hashTableSize;
	}
	int getNrOfElements() const{
		return nrOfElements;
	}
	int getNrOfCollisions() const{
		return nrOfCollisions;
	}
	void resetNrOfCollisions(){
		this->nrOfCollisions=0;
	}
};

#endif
