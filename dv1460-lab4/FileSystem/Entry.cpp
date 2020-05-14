#include <stdexcept>
#include "Entry.h"


Entry::Entry(int index, string name, Entry* parent) {
	this->parent = parent;
	this->index = index;
	this->setName(name);
}

Entry::~Entry(){}


char Entry::getIndex() {
	return index;
}

string Entry::getName() {
	return name;
}

Entry* Entry::getParent() {
	return parent;
}

void Entry::setName(string name) {
	if (name.size() > 100)
		throw length_error("name too long");
	this->name = name;
}

void Entry::setParent(Entry* parent) {
	this->parent = parent;
}
