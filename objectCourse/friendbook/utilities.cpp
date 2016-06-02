//
// Created by freak on 2015-11-06.
//

#include "utilities.h"

int findBook(FriendBook ** book,string owner ,int nrOf ){
    bool found = false;
    int index = -1;
    for (int i = 0; i < nrOf && !found; ++i) {

        if (book[i]->getOwner() == owner)
          {
              found = true;
              index = i;
          }
    }
    return index;
}
void readFromFile(FriendBook * arr)
{
    ifstream save;
    save.open("owners.txt");
    int nrOfFiles = 0;
    save >> nrOfFiles;
    string * fileNames = new string[nrOfFiles];
    save.ignore();
    for (int i = 0; i < nrOfFiles; ++i)
    {
        getline(save,fileNames[i]);
    }
    for (int j = 0; j < nrOfFiles; ++j)
    {
        string owner;
        int year;
        ifstream in;
        in.open(fileNames[j]);
        in.ignore();
        getline(in,owner);
        in >> year;
        arr[j] = FriendBook(owner,year);
    }
    for (int k = 0; k < nrOfFiles; ++k) {
        arr[k].readFromFile(fileNames[k]);
    }
    save.close();
}
void savetoFile(FriendBook * arr , int nrOfFiles)
{
    ofstream save;
    save.open("owners.txt");
    save << nrOfFiles << endl;
    string * fileNames = new string[nrOfFiles];
    for (int i = 0; i < nrOfFiles; ++i)
    {
      fileNames[i] = arr[i].getOwner()+".txt";
    }
    for (int j = 0; j < nrOfFiles; ++j)
    {
        ofstream out;

    }
    for (int k = 0; k < nrOfFiles; ++k) {
        arr[k].saveOnFile(fileNames[k]);
    }
}
