#ifndef Entry_H
#define Entry_H
#include <string>
#include <exception>
using namespace std;

class Entry
{
private:
	unsigned int index;
	string name;
	Entry* parent;

public:
	enum Type { EMPTY,FOLDER, FILE};

	Entry(int index, string name = "", Entry* parent = nullptr);
	virtual ~Entry();

	string getName();
	Entry* getParent();
	char getIndex();
	void setName(string name);
	void setParent(Entry* parent);
	virtual string toString() = 0;
};

#endif
