#include <iostream>
#include <ctime>
using namespace std;
void f(int i);
int main() {
    int array[100000];

    for (int i = 0; i < 100000; ++i)
    {
        array[i] = rand();
    }
    
    return 0;
}
template <typename T>
void swapThem(T ob1, T ob2){
    T temp;
    temp = ob1;
    ob1 = ob2;
    ob2 = temp;
}
template <typename T>
int partition(T arr[],int start, int end){
    T pivotValue = arr[start];
    int pivotpositon = start;
    for(int i= start+1; i <= end; i++){
        if(arr[i] < pivotValue){
            swapThem(arr[i],arr[pivotpositon+1]);
            swapThem(arr[pivotpositon],arr[pivotpositon+1]);
        }
    }
    return pivotpositon;
}
template <typename T>
void quicksort(T arr[], int start, int end){
    int pivot = partition(arr,start,end);
    quicksort(arr,start,pivot-1);
    quicksort(arr,pivot+1,end);
}
void f(int i ) {
    if(i >0 ) {
        cout << "i=" << i << endl;
        f(i - 1);
    }
    cout << " Lämnar med i= " << i << endl;
}