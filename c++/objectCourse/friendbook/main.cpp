#include <iostream>
#include "Friendbook.h"
#include "utilities.h"
using namespace std;
int menu();
void removefriend(FriendBook ** arr,int & nrof);
void addfriend(FriendBook ** arr,int  & nrOfBooks);
void addNewBook(FriendBook ** arr, int & nrOfBooks);
int getBookIndex(FriendBook ** arr,int & nrof);
void showAllBooks(FriendBook ** arr, int & nrOf);

void searchFriendByYear(FriendBook ** arr , int & nrof);
int main() {
    int sizeOfArr = 0;
    FriendBook **friendbooks = new FriendBook *[sizeOfArr];
    int nrofBooks = 0;
    readFromFile(*friendbooks);
    int n = 0;

    while (n != 500){
         n = menu();
        switch (n) {
            case 1: {
                if (nrofBooks == sizeOfArr) {
                    expand(friendbooks, sizeOfArr);
                }
                addNewBook(friendbooks, nrofBooks);
                break;
                case 2: {
                    addfriend(friendbooks, nrofBooks);
                    break;
                }
                case 3: {
                    removefriend(friendbooks, nrofBooks);
                    break;
                }
                case 4: {
                    searchFriendByYear(friendbooks, nrofBooks);
                    break;
                }
                case 5:{
                    showAllBooks(friendbooks,nrofBooks);
                }
                default: {

                }
            }
        }
}
        savetoFile(*friendbooks,nrofBooks);
    delete[] friendbooks;
    return 0;
}
void addNewBook(FriendBook ** arr, int & nrOfBooks){
    string owner;
    int year;
    cout << "owner? ";
    cin >>owner;
    cout << "year? ";
    cin >> year;
    if(findBook(arr,owner,nrOfBooks) == -1) {
        *arr[nrOfBooks]= FriendBook(owner, year);
        nrOfBooks++;
    }
    else{
        cout << "Det finns redan en sån bok\n";
    }
}
int menu(){
    int menuchoise = 0;
    cout << "1.add book\n"
      "2.add friend\n"
      "3.remove friend\n"
      "4.show by year\n";
       "4."
            ;
    cin >> menuchoise;
    return menuchoise;
}
void addfriend(FriendBook ** arr,int  & nrOfBooks){
    int book = getBookIndex(arr,nrOfBooks);
    if(book >= 0){
        cout << "\nname?";
        string name;
        cin.ignore();
        getline(cin,name);
        cout << "\nBirtDate?";
        int birthDate = 0;
        cin >>birthDate;
        arr[book]->addFriend(name,birthDate);
    }
}
void removefriend(FriendBook ** arr,int & nrof) {
    int book = getBookIndex(arr,nrof);
    if (book >= 0) {
        string name;
        cout << "name?\n";
        cin.ignore();
        getline(cin, name);
        int birthDate;
        cin >> birthDate;
    }
}
int getBookIndex(FriendBook ** arr,int & nrof){
    string owner;
    cout << "owner of Book?\n";
    cin >> owner;
    int book = findBook(arr, owner,nrof);
    return book;
}
void searchFriendByYear(FriendBook ** arr , int & nrof){
    int sizeofArr = 5;
    int year = 0;
    FriendBook ** owners = new FriendBook*[sizeofArr];
    int nrOfFoundBooks = 0;
    bool found= false;
    cout << year << endl;
    cin >> year;
    for (int i = 0; i <nrof; ++i) {
        found = arr[i]->showFriendsBornByYear(year);
        if(found == true)
        {
          if(sizeofArr==nrOfFoundBooks){
              expand(owners,sizeofArr);
          }
          owners[nrOfFoundBooks] = arr[i];
          nrOfFoundBooks++;
        }

    }
    for (int j = 0; j < nrOfFoundBooks; ++j) {
        cout << endl << owners[j]->getOwner() << endl;
        cout << owners[j]->showFriendsBornByYear(year)<< endl << endl;
    }

}
void showAllBooks(FriendBook ** arr, int & nrOf){
    for (int i = 0; i < nrOf; ++i) {
        cout << arr[i]->getOwner() << "\n " << arr[i]->show() << endl;
    }
}
