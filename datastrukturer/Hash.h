#ifndef HASH_H
#define HASH_H

template <typename Key>
class Hash
{
public:
	int operator()(const Key & key) const; 
};


#endif