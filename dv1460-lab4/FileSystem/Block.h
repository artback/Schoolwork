#ifndef BLOCK_H
#define BLOCK_H
#include <string>

using namespace std;
int const NROFELEMENTS=512;
class Block {
private:
	char bytes[NROFELEMENTS];

public:
	Block();
	~Block();

	char operator[](int index);

	void reset();
	void writeBlock(string data);
};

#endif
