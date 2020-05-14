#ifndef ENGLISHWORD_H
#define ENGLISHWORD_H

#include "Hash.h"
#include <iostream>

using namespace std;

class EnglishWord
{
private:
    string word;
public:
	EnglishWord(string word){
	 this->word=word;
	}
	EnglishWord(){
		this->word="";
	}
	EnglishWord(const EnglishWord& word){
		this->word = word.word;
	}
	~EnglishWord(){

	}
    void setValue(string word){
		this->word=word;
	}
	bool operator==(const EnglishWord& other)const{
		return(word == other.word);
	}
   	bool operator!=(const EnglishWord& other)const{
		return(word != other.word);
	}
	string getValue()const{
		return word;
	}
	// constructors, destructor..
	// memberfunctions
	// definition of == operator and != operator 
};
template<>
class Hash<EnglishWord>
{
public:
    int operator()(const EnglishWord& eng)
     {
	     unsigned int hash =2166136261;
	     unsigned int prime = 16777619;
         string word = eng.getValue();
		 for (unsigned int i = 0; i < word.length(); ++i) {
             hash =(hash*prime)^word[i];
		 }
        return hash;
     }
};

#endif
