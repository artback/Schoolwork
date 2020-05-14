#ifndef FILESYSTEM_H
#define FILESYSTEM_H
#include <string>
using namespace std;

#include "MemBlockDevice.h"
#include "Folder.h"
#include "File.h"
class FileSystem {
private:
	MemBlockDevice memBlockDevice;
	Folder* root;
	Folder* currentDir;
	void deleteNode(Entry* node);
	Entry* solvePath(string path);
	void splitPath(string& path, string& name);
public:
	FileSystem();
	~FileSystem();
	//format disk
	void format();
	//list files
	string ls(string path);
	//create new file
	bool createFile(string path, string name);
	//prints out file
	string cat(string path);
	// copy file
	bool cp(string origin, string destinaton);
	//create directory
	bool mkdir(string path);
	//change directory
	bool cd(string destination);
	//print working directory
	string pwd();
	//remove file
    string rm(string path);
    //remove directory
    string rmdir(string path);
	//create image as file
	void createImage(string filename);
	//restore image from file
	bool restoreImage(string filename);
};
#endif
