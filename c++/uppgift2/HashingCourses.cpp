/* eftersom antalet kurser är 14 blir en hashtabell med 17 entries utmärkt
 * det är ett primtal och det kommer finnas några platser över*/
#include "HashTableLinearProbing.h"
#include <fstream>
#include <sstream>
const int TABLE_SIZE = 17;
const int ARR_SIZE=14;
void toHash(HashTable<Course>&table,Course* arr);
void readFromfile(Course* arr);
void inHash(HashTable<Course>& hash);
int main(){
    HashTable<Course>table(TABLE_SIZE);
    Course arr[ARR_SIZE];
    readFromfile(arr);
    toHash(table,arr);
    string code = "DV1490";
    Course c(code);
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
        code.erase(6);
        getline(myfile,course);
        getline(myfile,points);
        arr[i] = Course(course,code,points);
        i++;
    }
}
void inHash(HashTable<Course>& hash) {
    string code = "";
    int index = -1;
    cout << endl << "input code please" << endl;
    code = "DV1460";
    Course c(code);
    index = hash.contains(c);
    cout << index;
    if (index != -1) {
        cout << endl << hash.get(index).getCode() << endl;
        cout << hash.get(index).getCourse() << endl;
        cout << hash.get(index).getPoints() << endl;
    }
    else {
        cout << "sorry not found" << endl;
    }
}


