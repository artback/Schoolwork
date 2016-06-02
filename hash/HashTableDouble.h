#ifndef HAStable_H
#define HAStable_H
#include <iostream>
#include "EnglishWords.h"

using namespace std;
template <typename HashElement>
class HashTable
{
private:
	int nrOfCollisions;
	int nrOfElements;
	int hashtableSize;
	HashElement** table;

	int probe(const HashElement& elem) const {
		static Hash<HashElement> myhash;
		unsigned long hash = 5381;
		hash = ((hash*33) + myhash(elem))%hashtableSize;
		return hash;
	}
	int myHash(const HashElement& elem) const {
		static Hash<HashElement> myhash;
		return (myhash(elem)%hashtableSize); // myhash(elem) is a call of the defined operator() for HashElement
    }
public:
	HashTable(int hashtableSize = 101){
    this->hashtableSize=hashtableSize;
    this->nrOfCollisions=nrOfElements=0;
	this->table= new HashElement*[hashtableSize];
    for (int i = 0; i < hashtableSize; ++i) {
	    table[i] = nullptr;
    }
    }
	HashTable(const HashTable& aTable){
		this->hashtableSize = aTable.hashtableSize;
		this->table = new HashElement*[hashtableSize];
		this->nrOfElements=aTable.nrOfElements;
		this->nrOfCollisions=aTable.nrOfCollisions;
		for (int j = 0; j < hashtableSize; ++j) {
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
		this->hashtableSize=aTable.hashtableSize;
		this->nrOfElements=aTable.nrOfElements;
		this->nrOfCollisions=aTable.nrOfCollisions;
		this->table = new HashElement[hashtableSize];
		for (int j = 0; j < hashtableSize; ++j) {
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
		int index = -1;
		int h= myHash(elem);
		int p = probe(elem);
		if(table[h] != nullptr) {
			if (*table[h] == elem) {
				index=h;
			}
			else {
				while (table[h] != nullptr && (index == -1)){
					if (*table[h] == elem) {
						index = h;
					}
					h = (h+p)%hashtableSize;
				}
			}
		}
		return index;
    }
	bool insert(const HashElement& elem) {
		int h = myHash(elem);
		bool inserted = false;
		if(table[h] == nullptr || table[h] == new HashElement("")) {
			table[h] = new HashElement(elem);
			nrOfElements++;
			inserted=true;
		}
		else {
			int p = probe(elem);
			while (table[h] != nullptr) {
				h = (h+p)%hashtableSize;
			}
			if(table[h] == nullptr || table[h] == new HashElement("")) {
				table[h] = new HashElement(elem);
				nrOfElements++;
				nrOfCollisions++;
				inserted=true;
			}
		}
		return inserted;
	}
	bool remove(const HashElement& elem) {
		int h= myHash(elem);
		int p = probe(elem);
		int index = contains(elem);
		bool remove=true;
		if (table[h] == nullptr) {
			remove= false;
		}
		else {
			if(*table[h] == elem ) {
				delete table[h];
				table[h] = new HashElement("");
			}
		}
		return remove;
	}
	void makeEmpty() {
		for (int i = 0; i < hashtableSize; ++i) {
			if (table[i] != nullptr) {
				delete table[i];
				table[i] = nullptr;
			}
		}
		nrOfElements=0;
		resetNrOfCollisions();
	}
	float loadFactor() const{
    return (double)nrOfElements/(double)hashtableSize;
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
