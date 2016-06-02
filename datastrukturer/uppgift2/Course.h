#ifndef ELEMENT_H
#define ELEMENT_H

#include "Hash.h"
#include <iostream>

using namespace std;
// hash-funktionen summerar
// ascii värdet på alla tecken i kurskoden det ger ett tillräckligt
//  unikt returvärde

class Course
{
private:
    string course;
    string points;
    string code;
public:
    Course(string course, string code, string points){
	 this->course=course;
	 this->code=code;
     this->points=points;
	}
    Course(){
      this->code="";
	  this->course="";
      this->points="0";
	}
    Course(string code){
      this->code=code;
      this->course="";
     this->points="0";
    }
    ~Course(){

	}
	bool operator==(const Course& other)const{
        return(this->code == other.code);
	}
   	bool operator!=(const Course& other)const{
        return(this->code != other.code);
    }
    void setCode(const string& code){
      this->code=code;
    }
    void setCourse(const string& course){
        this->course=course;
    }
    void setPoints(const string& points){
        this->points=points;
    }
    string getCode() const{
		return code;
	}
    string getCourse() const{
		return course;
	}
    string getPoints() const{
		return points;
	}
};
template<>
class Hash<Course>
{
public:
    int operator()(const Course& code)
     {
         string c = code.getCode();
         int value =0;
         for (int i = 0; i < c.length(); ++i) {
              value += c[i];
		 }
        return value;
	}
};

#endif
