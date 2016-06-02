/* eftersom antalet kurser är 14 blir en hashtabell med 23 entries utmärkt
 * det är ett primtal och det kommer ge en loadfactor på 0.6 där över 0.7
 * kommer minska verkningsraden på hashtabellen icke linjäriskt */
#include "HashTableLinearProbing.h"
#include <fstream>
#include <sstream>
const int TABLE_SIZE = 23;
const int ARR_SIZE=14;
void toHash(HashTable<Course>&table,Course* arr);
void readFromfile(Course* arr);
void inHash(HashTable<Course>& hash);
int main(){
    HashTable<Course>table(TABLE_SIZE);
    Course arr[ARR_SIZE];
    readFromfile(arr);
    toHash(table,arr);
    inHash(table);
    return 0;
}
void toHash(HashTable<Course>&table,Course* arr){
    for (int i = 0; i < ARR_SIZE; ++i)
    {
            table.insert(arr[i]);
    }
}
void readFromfile(Course* arr){
    ifstream myfile;
    myfile.open("courses.txt");
    string course = "";
    string code = "";
    string points="";
    int i = 0;
    while(getline(myfile,code)){
        code.erase(code.length()-1);
        getline(myfile,course);
        getline(myfile,points);
        arr[i] = Course(course,code,points);
        i++;
    }
}
void inHash(HashTable<Course>& hash) {
    HashTable<Course>  t = hash ;
    string code = "";
    int index = -1;
    cout << endl << "input code please" << endl;
    cin >> code;
    Course c(code);
    index = t.contains(c);
    if (index != -1) {
        cout << endl << hash.get(index).getCode() << endl;
        cout << hash.get(index).getCourse() << endl;
        cout << hash.get(index).getPoints() << endl;
    }
    else {
        cout << "sorry not found" << endl;
    }
}


