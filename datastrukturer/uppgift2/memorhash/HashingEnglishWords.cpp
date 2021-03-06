/* eftersom antalet ord är 999 blir en hashtabell med 397 entries utmärkt
 * det är ett primtal och eftersom vi har kedjning så kommer vi få en loadfactor
 * på medelvärde 2.
 * */
#include "EnglishWords.h"
#include <fstream>
#include <sstream>
#include "HashTableChaining.h"

const int ARR_SIZE=999;
const int TABLE_SIZE=397;
void toHash(HashTable<EnglishWord>&table,EnglishWord* arr, int aSize);
void readFromfile(EnglishWord* arr);
void inHash(HashTable<EnglishWord>& hash);
int main(){
    HashTable<EnglishWord>table(TABLE_SIZE);
    EnglishWord arr[ARR_SIZE];
    readFromfile(arr);
    toHash(table,arr,ARR_SIZE);
    inHash(table);
    return 0;
}

void toHash(HashTable<EnglishWord>&table,EnglishWord* arr, int aSize){
    for (int i = 0; i < aSize; ++i)
    {
            table.insert(arr[i]);
    }
}
void readFromfile(EnglishWord* arr){
    ifstream myfile;
    myfile.open("engWords.txt");
    string str = "";
    int i = 0;
    while(getline(myfile,str)) {
        str.erase(str.length()-1);
        arr[i] = EnglishWord(str);
        i++;
    }
}
void inHash(HashTable<EnglishWord>& hash) {
    string sentence = "";
	string word = "";
    EnglishWord a("a");
    hash.remove(a);
    char dot = '.';
    cout << "input sentence please" << endl;
    getline(cin,sentence);
    istringstream iss(sentence);
    while(iss >> word  ) {
            bool b = hash.contains(word);
			char last = word[word.length() - 1];
			if ( last != dot){
				if (!b){
					cout << word << endl;
				}
			}
        }
	getchar();
}
