//
#ifndef JUMPER_H
#define JUMPER_H
#include <string>

using namespace std;

class Jumper
{
private:
    string name;
    int nr;
    int nrOfJumps;
    int nrOfMadeJumps;
    int *results;
public:
    Jumper();



    ~Jumper();
    Jumper operator=(Jumper const& other);
    void setName(string name);
    void setNr(int nr);
    void setnrOfJumps(int nrOfJumps);
    void addResult(int jump, int length);
    string getName() const;
    int getNr() const;
    int getResult(int jump) const;
    string tostring() const;
};

#endif //JUMPER_JUMPER_H
