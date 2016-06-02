#ifndef ELEMENT_H
#define ELEMENT_H

#include "Hash.h"

class Element
{
private:
	// membervariables
public:
	// constructors, destructor, ...
	// memberfunctions
	// definition of == operator and != operator 
};

template<>
class Hash<Element>
{
public:
	int operator()(const Element& elem) const
	{
		// to be implement
		// the integer returned is calculated from data in HashElement
		return -1;
	}
};

#endif
