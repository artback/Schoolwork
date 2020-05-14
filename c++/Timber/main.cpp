#include <iostream>
#include "WainScoting.h"
using namespace std;

#include "Register.h"
int menu();
void addWainscoting(Register& wood);
void fillString(Register & wood);
void changeIfPainted(Register & wood);
void remove(Register & wood);
void addRuleTimber(Register & wood);
int main() {
    Register wood;
    bool loop = true;
    while(loop) {
        int menuchoise = menu();
        switch (menuchoise) {
            case 0:
                loop = false;
                break;
            case 1:
                addWainscoting(wood);
                break;
            case 2:
                addRuleTimber(wood);
                break;
            case 3 :
                fillString(wood);
                break;
            case 4:
                remove(wood);
                break;
            case 5:
                changeIfPainted(wood);
                break;
            default:
                cout << "wrong number\n";
                break;
        }
    }
    return 0;
}
/*int main(){
    //skapar 2 register objekt R och B
    {
        Register R;
        Register B;
        R.addRuleT(10, 10, 10,"CK51");
        //Tilldelningsoperator
        //B tilldelas register R
        B = R;
        int size = B.nrsOfTimber();
        string * arr = new string[size];
        B.ruleTtostring(arr);
        cout << arr[0] << endl;

        delete[] arr;
        arr = nullptr;
        // här testas copykonstruktorn
        // objekt d skapas som en kopia av R
        Register d(R);
        size = d.nrsOfTimber();
        arr = new string[size];
        d.allToString(arr);
        for (int i = 0; i < size; ++i)
        {
           cout << arr[i] << endl;
        }
        delete[] arr;
        arr = nullptr;
    }
    //här är objekten förstörda


}*/  //test av cctor destruktor och =opertor
int menu(){
    int chooise = 0;
        cout << "0.close Program \n"
                "1.add Wainscoting \n"
                "2.add Ruletimber\n"
                "3.fill string\n"
                "4.Remove Timber\n"
                "5.Change if painted\n";
        cin >> chooise;
    return chooise;
}
void addWainscoting(Register & wood){
    int height,width,price;
    string profile;
    char painted;
    cout <<"height?\n";
    cin >> height;
    cout << "width?\n";
    cin >> width;
    cout << "price?\n";
    cin >> price;
    cout << "profile?\n";
    cin >> profile;
    cout << "painted? y/n \n";
    cin >> painted;
  wood.addWain(height,width,price,profile,painted);
}
void addRuleTimber(Register & wood){
    int height,width,price;
    string classified;
    cout <<"height?\n";
    cin >> height;
    cout << "width?\n";
    cin >> width;
    cout << "price?\n";
    cin >> price;
    cout << "Class?\n";
    cin >> classified;
    wood.addRuleT(height,width,price,classified);
}
void fillString(Register & wood){
    string chooise;
    int nrof =0;
    cout << "all,wainscotting or ruletimber?\n";
    cin >>chooise;
    if(chooise == "wainscotting"){
        nrof = wood.nrsOfWainScoting();
        string * arr = new string[nrof];
        for (int i = 0; i < nrof; ++i)
        {
            wood.wainToString(arr);
        }
        for (int j = 0; j < nrof; ++j)
        {
            cout << arr[j];
        }
        delete[] arr;
        arr = nullptr;
    }
    else if(chooise == "ruletimber") {
        nrof = wood.nrsOfRuleTimber();
        string *arr = new string[nrof];
        for (int i = 0; i < nrof; ++i) {
            wood.ruleTtostring(arr);
        }
        for (int j = 0; j < nrof; ++j) {
            cout << arr[j];
        }
        delete[] arr;
        arr = nullptr;
    }
    else{
        nrof = wood.nrsOfTimber();
        string * arr = new string[nrof];
        for (int i = 0; i < nrof; ++i) {
            wood.allToString(arr);
        }
        for (int j = 0; j < nrof; ++j) {
            cout << arr[j];
        }
        delete[] arr;
        arr = nullptr;
    }
    cout << endl << endl;
}
void remove(Register & wood){
   int height, width, price;
    cout << "height?\n";
    cin>> height;
    cout << "width?\n";
    cin >>width;
    cout << "price?\n";
    cin>>price;
    wood.remove(height,width,price);
}
void changeIfPainted(Register & wood){
    int height, width;
    string profile;
    char painted;
    cout << "height?\n";
    cin>> height;
    cout << "width?\n";
    cin >>width;
    cout << "profile?\n";
    cin>>profile;
    int index = wood.findWain(height,width,profile);
    if(index >= 0){
        cout << "painted? y/n\n";
        cin >> painted;
        wood.changePainted(index,painted);
    }
}
