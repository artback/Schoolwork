#ifndef FILE_H
#define FILE_H
#include "Entry.h"
#include "Folder.h"

class File : public Entry {
private:
	string content;
public:
	File(int index, string name = "", Folder* parent = nullptr);
	virtual ~File();
	string getContent();
	void   setContent(string content);

	string toString();
};

#endif
