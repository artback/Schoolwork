#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <iostream>
#include "EnglishWords.h"


using namespace std;

template <typename HashElement>
class HashTable {
private:
	// internal datastructure
	int nrOfCollisions ;
	int nrOfElements;
	int hashTableSize;
	HashElement **table;

	int myHash(const HashElement &elem) const {
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
		hashTableSize=aTable.hashTableSize;
        this->table= new HashElement*[hashTableSize];
		this->nrOfCollisions=aTable.nrOfCollisions;
		for (int j = 0; j < hashTableSize; ++j) {
			this->table[j]= new HashElement(*aTable.table[j]);
			HashElement* nowTable= table[j];
			HashElement* nowaTable = aTable.table[j];
            while(nowaTable->next != nullptr ){
                nowaTable = nowaTable->next;
                nowTable = nowTable->next;
                nowTable->next(new HashElement(*nowaTable));
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
		this->table = new HashElement*[hashTableSize];
		for (int j = 0; j < hashTableSize; ++j) {
			this->table[j]= new HashElement(aTable.table[j]);
			HashElement* nowTable= table[j];
			HashElement* nowaTable = aTable.table[j];
            while(nowaTable->next != nullptr ){
                nowaTable = nowaTable->next;
                nowTable = nowTable->next;
                nowTable->next(new HashElement(*nowaTable));
			}
		}
		return *(this);
	}
    bool contains(const HashElement& elem) const{
		int index = myHash(elem);
        if(table[index] == nullptr){ return false; }
        else if (table[index]->getValue() == elem.getValue()) { return true; }
        HashElement* walker = table[index];
        while( walker->next != nullptr && (walker->getValue() != elem.getValue()))
        { walker = walker->next;}
        if (walker == nullptr) { return false; }
        return true;
	}
	bool insert(const HashElement& elem){
         int index = myHash(elem);
         if(table[index] == nullptr){
		   table[index]  = new HashElement(elem);
		   nrOfElements++;
	      }
		else{
	        HashElement* now = table[index];
            while(now->next != nullptr ){
              now = now->next;
			}
            now->next = new HashElement(elem);
	        nrOfElements++;
	        nrOfCollisions++;
	     }
		return true;
	}
	bool remove(const HashElement& elem) {
		int index = myHash(elem);
		if (table[index] == nullptr) { return false; }
		HashElement* prev = nullptr;
		bool removed = true;
		HashElement* now= table[index];
        while(now->next != nullptr && elem.getValue() != now->getValue()){
	     prev = now;
         now = now->next;
        }
		if(now->getValue() != elem.getValue()){
			removed = false;
		}
        else if(now->next != nullptr){
            prev->Next = now->next;
		}
        return removed;
	}
	void makeEmpty() {
		for (int i = 0; i < hashTableSize; ++i) {
			HashElement* now = table[i];
			HashElement* prev = nullptr;

			if (table[i] != nullptr) {

                while(now->next != nullptr){
					prev = now;
                    now = now->next;
                    delete prev;
				}
				if(now != nullptr) {
					delete now;
				}
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
