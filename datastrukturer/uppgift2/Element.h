#ifndef ELEMENT_H
#define ELEMENT_H

#include "Hash.h"
#include <iostream>

using namespace std;

class Element
{
private:
	// membervariables
public:
	Element();
	~Element();
	bool operator==(const Element& other);
   	bool operator!=(const Element& other);

	// constructors, destructor..
	// memberfunctions
	// definition of == operator and != operator 
};
template<>
class Hash<Element>
{
public:
	int operator()(const Element& elem)
	 {
		// to be implement
		// the integer returned is calculated from data in HashElement
		return -1;
	}
};

#endif