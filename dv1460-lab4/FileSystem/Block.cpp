#include "Block.h"
#include <stdexcept>

Block::Block() {
	reset();
}

Block::~Block(){}

char Block::operator[](int index) {
	if (index < 0 || index >= NROFELEMENTS)
		throw out_of_range("index out of range");
	return bytes[index];
}

void Block::reset() {
	for (int i = 0; i < NROFELEMENTS ; ++i) {
		bytes[i] ='\0';
	}
}

void Block::writeBlock(string data) {
	for (int i = 0; i < data.size(); ++i) {
		bytes[i]=data[i];
	}
}

