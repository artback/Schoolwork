#include <iostream>
//#include "Media.h"
#include "NewsPaper.h"
#include "Books.h"
using namespace std;

int main() {
    Media* arr[6];
    arr[0] = new NewsPaper;
    arr[1] = new Books;
    arr[2] = new NewsPaper;
    arr[3] = new Books;
    for (int i = 0; i < 4; ++i) {
        NewsPaper* n=dynamic_cast<NewsPaper*>(arr[i]);
        if(n != nullptr){
            cout << n->toString();
        }
        else {
            Books* b=dynamic_cast<Books*>(arr[i]);
        if(b != nullptr){
           cout << b->toString();
        }
        }
    }
 return 0;
}