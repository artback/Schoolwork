#ifndef FOLDER_H
#define FOLDER_H
#include <vector>
#include "Entry.h"

class Folder : public Entry
{
private:
	vector<Entry*> children;
public:
	Folder(int index, string name = "", Folder* parent = nullptr);
	virtual ~Folder();

	void addChild(Entry* child);
	Entry* getChild(string name);
	bool removeChild(Entry* child);
	
	int size();
	Entry* operator[](int index);

	string toString();
};

#endif
