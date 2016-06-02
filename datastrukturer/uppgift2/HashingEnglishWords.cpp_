/* eftersom antalet kurser är 14 blir en hashtabell med 17 entries utmärkt
 * det är ett primtal och det kommer finnas en plats över */
#include "Course.h"
#include <fstream>
#include "HashTableLinearProbing.h"

void toHash(HashTable<Course>&table,Course* arr, int aSize);
void readFromfile(Course* arr);
void inHash(HashTable<Course>& hash);
int main(){
    HashTable<Course>table(21);
    Course arr[17];
    readFromfile(arr);
    toHash(table,arr,17);
    inHash(table);
    return 0;
}

void toHash(HashTable<Course>&table,Course* arr, int aSize){
    for (int i = 0; i < aSize; ++i)
    {
        if(arr[i].getCode() != "") {
            table.insert(arr[i]);
        }
    }
}
void readFromfile(Course* arr){
    ifstream myfile;
    myfile.open("courses.txt");
    string str = "";
    int i = 0;
    while(getline(myfile,str)){
        arr[i].setCode(str);
        getline(myfile,str);
        arr[i].setCourse(str);
        getline(myfile,str);
        arr[i].setPoints(str);
        i++;
    }
}
void inHash(HashTable<Course>& hash) {
    string code = "";
    cout << "input code please" << endl;
    code = "DV1537";
    Course c(code);
    int index = hash.contains(c);
    if(index > -1 ) {
        cout << hash.get(1).getCode() << endl;
        cout << hash.get(1).getCourse() << endl;
        cout << hash.get(1).getPoints() << endl;
    }
}
