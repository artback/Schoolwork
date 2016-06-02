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
        HashElement* walker = nullptr;
        HashElement* aTableWalker= nullptr;
		for (int j = 0; j < hashTableSize; ++j) {
            if(aTable.table[j]){
				table[j] = new HashElement(*aTable.table[j]);
                walker= table[j];
                aTableWalker= aTable.table[j];
                while(aTableWalker->next){
                    aTableWalker=aTableWalker->next;
                    walker->next = new HashElement(*aTableWalker);
                    walker=walker->next;
                }
            }
            else{
                table[j]=nullptr;
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
        HashElement* walker = nullptr;
        HashElement* aTableWalker= nullptr;
		this->hashTableSize=aTable.hashTableSize;
		this->table = new HashElement*[hashTableSize];
        for (int j = 0; j < hashTableSize; ++j) {
            if(aTable.table[j]){
				table[j] = new HashElement(*aTable.table[j]);
                walker= table[j];
                aTableWalker= aTable.table[j];
                while(aTableWalker->next){
                    aTableWalker=aTableWalker->next;
                    walker->next = new HashElement(*aTableWalker);
                    walker=walker->next;
                }
            }
            else{
                table[j]=nullptr;
            }
        }
		return *(this);
	}
    bool contains(const HashElement& elem) const{
		int index = myHash(elem);
	    bool contains = false;
        HashElement* walker = table[index];
        if(table[index] == nullptr) { return false;}
        else if (*table[index] == elem)
        {contains=true;}
	    else{
		    while (walker->next != nullptr && (*walker != elem))
		    { walker = walker->next; }
		    if(*walker == elem){
			    contains=true;
		    }
	    }
	    return contains;
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
		bool removed = false;
		HashElement* now = table[index];
        if (table[index] == nullptr){}
        else if (*table[index] == elem)
        {
            HashElement* next = table[index]->next;
            delete now;
            table[index] = next;
        }
		else{
            HashElement* prev = nullptr;
            while (now != nullptr && (elem != *now)){
                prev = now;
                now=now->next;
            }
            if(*now == elem){
                         if (now->next){
                         prev->next= now->next;
                         }
                         delete now;
                       removed = true;
                   }
        }
return removed;
}

void makeEmpty() {
    for (int i = 0; i < hashTableSize; ++i) {
        HashElement *now = table[i];
        HashElement *prev = nullptr;
        if (table[i] != nullptr) {
            while (now != nullptr) {
                prev = now;
                now = now->next;
                delete prev;
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
