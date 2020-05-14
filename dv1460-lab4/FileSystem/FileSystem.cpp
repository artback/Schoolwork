#include "FileSystem.h"
#include <sstream>


void FileSystem::deleteNode(Entry* entry) {
	((Folder*)entry->getParent())->removeChild(entry);
	memBlockDevice.freeBlock(entry->getIndex());
	delete entry;
}

Entry* FileSystem::solvePath(string path) {
	string field;
	stringstream stream;
	Entry* output;
	Folder* parent;
	if (path[0] == '/') {
		output = root;
		stream.str(path.substr(1));
	}else{
		output = currentDir;
		stream.str(path);
	}
	while (getline(stream, field, '/') && output != nullptr){
		if (field == "." );
		else if (field == "..") {
			output = output->getParent();
		} else {
			parent = dynamic_cast<Folder*>(output);
			if(parent){
				output = parent->getChild(field);
			}
		}
	}
	return output;
}

void FileSystem::splitPath(string & path, string & name) {
	string originalPath = path;
	int lastSlash = originalPath.find_last_of('/');

	if (lastSlash < originalPath.size() - 1)
	{
		path = originalPath.substr(0, lastSlash);
		name = originalPath.substr(lastSlash + 1);
	}
	else
	{
		path = ".";
		name = originalPath.substr(0, lastSlash);
	}
}

FileSystem::FileSystem() {
	root = new Folder(0);
	root->setParent(root);
	currentDir = root;
	memBlockDevice.writeBlock(root);
}

FileSystem::~FileSystem() {
	deleteNode(root);
}

void FileSystem::format() {
	deleteNode(root);
	root = new Folder(0);
	root->setParent(root);
	currentDir = root;
	memBlockDevice.writeBlock(root);
}

void FileSystem::createImage(string filename) {
	memBlockDevice.saveImage(filename);
}

bool FileSystem::restoreImage(string filename) {
	MemBlockDevice temp;
	bool output = temp.loadImage(filename);

	if (output) {
		deleteNode(root);
		memBlockDevice = temp;
		root = (Folder*)memBlockDevice.readBlock(0);
		root->setParent(root);
		currentDir = root;
	}

	return output;
}

string FileSystem::ls(string path) {
	string output = "";
	Entry* entry = solvePath(path);
	Folder* dir = dynamic_cast<Folder*>(entry);
	Entry* child;

	if (dir) {
		for (int i=0;i < dir->size(); i++) {
			child = (*dir)[i];
			if(dynamic_cast<Folder*>(child)){
				//Set output to red color For directory
				output += "\33[31m" +child->getName() + "\033[0m";
			}else{
				output += child->getName();
			}if(i < dir->size()-1){
				output+= ", ";
			}
		}
	}else{
		File* file = dynamic_cast<File*>(entry);
		if(file){
			output = file->getName();
		}else{
		output += "ls: cannot access '" + path + "': No such file or directory";
        }
	}

	return output;
}

bool FileSystem::createFile(string path, string content) {
	bool output = false;
	string name;

	splitPath(path, name);

	Folder* parent = dynamic_cast<Folder*>(solvePath(path));

	if (parent) {
		if (!parent->getChild(name)) {
			int index = memBlockDevice.getFreeBlock();
			if (index != -1) {
				File* file = new File(index, name, parent);
				file->setContent(content);
				parent->addChild(file);
				memBlockDevice.writeBlock(file);
				memBlockDevice.writeBlock(parent);
				output = true;
			}
		}
	}

	return output;
}

string FileSystem::cat(string path) {
	string output = "cat: "+ path+ ": No such file or directory";
	File* file = dynamic_cast<File*>(solvePath(path));

	if (file) {
		output = file->getContent();
	}

	return output;
}

bool FileSystem::cp(string path1, string path2) {
	bool output = false;

	File* original = dynamic_cast<File*>(solvePath(path1));

	if (original) {
		output = createFile(path2, original->getContent());
	}


	return output;
}

bool FileSystem::mkdir(string path) {
	bool output = false;

	string name;
	splitPath(path, name);

	Folder* parent = dynamic_cast<Folder*>(solvePath(path));

	if (parent){
		if (!parent->getChild(name))
		{
			int index = memBlockDevice.getFreeBlock();
			if (index != -1) {
				Folder* folder = new Folder(index, name, parent);
				parent->addChild(folder);

				memBlockDevice.writeBlock(folder);
				memBlockDevice.writeBlock(parent);

				output = true;
			}
		}
	}

	return output;
}


bool FileSystem::cd(string path) {
	bool output = false;
	Entry* solved = solvePath(path);
	Folder* folder = dynamic_cast<Folder*>(solved);
	if (folder) {
		currentDir= folder;
		output = true;
	}

	return output;
}

string FileSystem::pwd() {
	string output = "";

	Entry* walker = currentDir;
	if(walker != root){
		output="/";
	}else {
		while (walker != root) {
			output = "/" + walker->getName() + output;
			walker = walker->getParent();
		}
	}


	return output;
}

string FileSystem::rm(string path) {
	string output= "";
	Entry* remove = solvePath(path);
	if (remove) {
		Folder* folder = dynamic_cast<Folder*>(solvePath(path));
		if(folder){
			output = " rm: cannot remove '"+ path +"': Is a directory";
		}else {
			deleteNode(remove);
		}
	}
	return output;
}
string FileSystem::rmdir(string path) {
	string output = "";
	Entry* remove = solvePath(path);
	if(remove && remove != root){
			Folder* folder = dynamic_cast<Folder*>(remove);
			if(folder){
                if(folder->size() != 0){
                        output="rmdir: failed to remove '"+ path +"': Directory not empty";
                }else{
                        deleteNode(remove);
                }
            }else{
				output="rmdir: failed to remove '"+ path +"': Not a directory";
			}
    }else{
		output="rmdir: failed to remove '"+ path +"': Not a directory";
	}
	return output;
}