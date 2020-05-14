#ifndef HASH_H
#define HASH_H

#include <iostream>

using namespace std;
template <typename Key>
class Hash
{
public:
    int operator()(const Key & key) const;
 };

#endif
