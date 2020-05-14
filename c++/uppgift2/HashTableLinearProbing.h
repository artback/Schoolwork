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
		this->hashTableSize=aTable.hashTableSize;
		this->table= new HashElement*[hashTableSize];
		this->nrOfCollisions=aTable.nrOfCollisions;
		for (int j = 0; j < nrOfElements; ++j) {
            this->table[j]=new HashElement(aTable.table[j]);
		}
		for (int j = 0; j < hashTableSize-nrOfElements; ++j) {
			table[j] = nullptr;
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
		this->table = new HashElement[hashTableSize];
		for (int j = 0; j < hashTableSize; ++j) {
           this->table[j]=new HashElement(aTable.table[j]);
		}
        return *(this);
	}
	int contains(const HashElement& elem) const {
	int index = myHash(elem);
	if(table[index] != nullptr) {
		if (table[index]->getCode() == elem.getCode()) {
			return index;
		}
	}
	return index;
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
		int index = contains(elem);
		if (index == -1) {
			return false;
		}
		delete table[index];
		table[index] = nullptr;
		while (table[index + 1] != nullptr) {
		        HashElement *temp = table[index + 1];
		        delete (table[index + 1]);
		        insert(*temp);
		        delete temp;
		        index++;

	        }
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
	}
	double loadFactor() const{
    return nrOfElements/hashTableSize;
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
