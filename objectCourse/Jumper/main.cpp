#include <iostream>
#include "Jumper.h"
using namespace std;
void printJumper(Jumper j);
Jumper createJumper();
int main() {
     Jumper jmp1("Kalle",1,4);
    jmp1.addResult(1,111);
    jmp1.addResult(3,333);
    cout << jmp1.tostring();

            jmp1.addResult(1,111);
    Jumper jmp2(jmp1);
    jmp2.setNr(2);
    jmp2.addResult(2,222);
    jmp2= createJumper();
    Jumper jmp3 = jmp2;
    jmp3.addResult(4,3333);
    cout << jmp2.tostring();
    cout << jmp1.tostring();
    cout << jmp3.tostring();
    return 0;
}
void printJumper(Jumper j){
        cout << j.tostring();
}
Jumper createJumper(){
    Jumper j("Kalle",2,5);
    return j;
}
