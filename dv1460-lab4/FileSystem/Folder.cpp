#include "Folder.h"






Folder::Folder(int index, string name, Folder * parent) : Entry(index, name, parent) {
}

Folder::~Folder(){}

void Folder::addChild(Entry * child) {
	children.push_back(child);
}

Entry* Folder::getChild(string name) {
	Entry* output = nullptr;
	for (int i = 0; i < children.size() && output == nullptr; i++) {
		if (name == children[i]->getName()) {
			output = children[i];
		}
	}

	return output;
}

bool Folder::removeChild(Entry * child) {
	bool output = false;
	for (int i = 0; i < children.size() && output == false; i++){
		if (child == children[i]){
			children.erase(children.begin()+i);
			output = true;
		}
	}
	return output;
}

int Folder::size() {
	return children.size();
}

Entry * Folder::operator[](int index) {
	return children[index];
}

string Folder::toString() {
	string output = "";

	output += (char)Entry::FOLDER;
	output += getName();
	output += (char)0;

	for (int i = 0 ;i < children.size(); i++) {
		output += children[i]->getIndex();
	}


	return output;
}
