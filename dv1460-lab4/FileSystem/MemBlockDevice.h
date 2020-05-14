#include "Block.h"
#include "Folder.h"
#include "File.h"

class MemBlockDevice
{
private:
	Block blocks[250];
public:
	MemBlockDevice();
	~MemBlockDevice();

	char getFreeBlock();

	void writeBlock(Entry* node);
	void writeBlockString(int index, string blockString);
	Entry* readBlock(int index, Folder * parent = nullptr);
	void freeBlock(unsigned int index);

	void saveImage(string filename);
	bool loadImage(string filename);

};

