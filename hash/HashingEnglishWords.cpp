/* eftersom antalet ord är 999 blir en hashtabell med 1999 entries utmärkt
 * det är ett primtal och eftersom  vi får en loadfactor
 * på strax under 0.5 vilket är det optimala för en hashtabell av denna typ.
    hash-funktionen är en beprövad som kallas fnv1 den har ett offset värde hash som
    är fördefinerad för optimal prestanda beroende på returnerande hashstorlek
    och ett primtal som den multipliceras med detta görs sedan logiskt xor med strängen (tecken för tecken).
    probe-funkotionen är av typen DJB2 den tar värdet av hashfuntionen + hash*33
    hash satt till startvärde 5381 . proben används sedan som  step så varje gång insert/contains
    träffar ett värde som inte är det som eftersträvas så adderas denna step på.
    */

 #include "EnglishWords.h"
#include <fstream>
#include <sstream>
#include "HashTableDouble.h"

const int ARR_SIZE=999;
const int TABLE_SIZE=1999;
void toHash(HashTable<EnglishWord>&table,EnglishWord* arr, int aSize);
void readFromfile(EnglishWord* arr);
void inHash(HashTable<EnglishWord>& hash);
void insertHash(HashTable<EnglishWord>&table);
void removeFromHash(HashTable<EnglishWord>& hash);
int main(){
    bool loop = true;
    HashTable<EnglishWord>table(TABLE_SIZE);
    EnglishWord arr[ARR_SIZE];
    readFromfile(arr);
    toHash(table,arr,ARR_SIZE);
    while(loop) {
        int number=0;
        cout << "\n1.add new word \n2.check sentence\n3.remove word\n4.end program\n";
        cin >> number;
        switch (number) {
            case 1:
                insertHash(table);
                break;
            case 2:
                inHash(table);
                break;
              case 3:
                removeFromHash(table);
                break;
            case 4:
                loop=false;
                break;
        }
    }
    table.makeEmpty();
    return 0;
}

void toHash(HashTable<EnglishWord>&table,EnglishWord* arr, int aSize){
    for (int i = 0; i < aSize; ++i)
    {
        if(arr->getValue() != "")
            table.insert(arr[i]);
    }
    cout << "the loadfactor is: " << table.loadFactor() << endl;
}
void insertHash(HashTable<EnglishWord>&table){
    string word;
    int ins;
    cout << "input new word";
    cin >> word;
    EnglishWord eWord(word);
    ins = table.contains(word);
    if (ins != -1) { cout << word << " is already in table"; }
    else{
        table.insert(eWord);
    }
}
void readFromfile(EnglishWord* arr){
    ifstream myfile;
    myfile.open("C:/temp/engWords.txt");
    string str = "";
    int i = 0;
    while(getline(myfile,str)) {
        str.erase(str.length()-1);
        arr[i] = Englis
        hWord(str);
        i++;
    }
}
void inHash(HashTable<EnglishWord>& hash) {
    string sentence = "";
	string word = "";
    char dot = '.';
    cout << "input sentence please" << endl;
    cin.ignore();
    getline(cin,sentence);
    istringstream iss(sentence);
    bool notdot = false;
    while(iss >> word && !notdot) {
            char last = word[word.length() - 1];
            if ( last == dot){
            word.pop_back();
            notdot=true;
             }
            int b = hash.contains(word);
				if (b == -1){
					cout << word << endl;
				}
    }
}
void removeFromHash(HashTable<EnglishWord>& hash){
string word = "";
cout << "input word please" << endl;
cin >> word;
bool b = hash.remove(word);
if (!b){
cout << "word is not in table" << endl;
}else{
cout << "word removed" << endl;
}
}
