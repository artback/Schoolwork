#include "MemBlockDevice.h"
#include <fstream>

int const LASTELEMENT=NROFELEMENTS-1;
MemBlockDevice::MemBlockDevice(){}
MemBlockDevice::~MemBlockDevice(){}

char MemBlockDevice::getFreeBlock() {
	int output = 255;
	for (int i = 1; i < 250 && output == 255; i++) {
		if (blocks[i][0] == 0) {
			output = i;
		}
	}

	return output;
}

void MemBlockDevice::writeBlock(Entry* entry) {
		string entryString = entry->toString();
		writeBlockString(entry->getIndex(), entryString);
}
void MemBlockDevice::writeBlockString(int index,string blockString) {
	string string1 = string(512,'\0');
	for (int i=0; i < blockString.size() && i < NROFELEMENTS-1; ++i) {
        string1[i] = blockString[i];
	}
	if(blockString.size() < NROFELEMENTS-1 ){
		string1[LASTELEMENT] = -1;
		blocks[index].writeBlock(string1);
	}else {
		blocks[index].writeBlock(string1);
		char nextBlock = getFreeBlock();
		if(nextBlock != 255) {
			string1[LASTELEMENT] = nextBlock;
			blocks[index].writeBlock(string1);
			writeBlockString(nextBlock, blockString.substr(511, blockString.size()));
		}
	}
}
Entry* MemBlockDevice::readBlock(int index, Folder* parent) {
	if (index < 0 || index >= 250)
		throw out_of_range("Block out of range");
	Entry* output = nullptr;
	string block =string(512,'\0');
	for (int j = 0; j < 512; ++j) {
		block[j]=blocks[index][j];
	}
		char tempIndex = blocks[index][LASTELEMENT];
		bool apa = false;
        while (tempIndex != '\377') {
	        apa=true;
            string value = string(511,'\0');
            for (int j = 0; j < 511; ++j) {
                value[j] = blocks[tempIndex][j];
            }
            for (int i = 0; i < value.size(); ++i) {
                block.push_back(value[i]);
            }
            tempIndex = blocks[tempIndex][LASTELEMENT];
        }
		int null1 = block.find((char)0);
		int null2 = block.find((char)0, null1 + 1);
		string name = block.substr(1, null1-1);
        string content = block.substr(null1+ 1, null2-(null1 + 1));
		if (block[0] == Entry::FOLDER) {
			Folder *folder = new Folder(index, name, parent);
			for (int i = 0; i < content.size(); i++)
				folder->addChild(readBlock(content[i], folder));
			output = folder;
		} else if (blocks[index][0] == Entry::FILE) {
			File *file = new File(index, name, parent);
			file->setContent(content);
			output = file;
		}
	return output;
}

void MemBlockDevice::freeBlock(unsigned int index) {
	while(blocks[index][LASTELEMENT] != -1) {
		blocks[index].reset();
		index = blocks[index][LASTELEMENT];
	}
}
void MemBlockDevice::saveImage(string filename) {
	ofstream file(filename, ios::binary);
	file.write((char*)blocks, sizeof(blocks));
	file.close();
}
bool MemBlockDevice::loadImage(string filename) {
	bool output = false;
	ifstream file(filename, ios::binary);
	if (file) {
		file.read((char*)blocks, sizeof(blocks));
		output = true;
	}

	file.close();
	return output;
}

