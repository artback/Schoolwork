#include <iostream>

using namespace std;

#include "Circle.h"
#include "List.h"
int main() {
    List<Circle> item;
    Circle c;
    item.add(c);
    item.add(c);
    cout << c;
    return 0;
}