#ifndef ENGLISHWORD_H
#define ENGLISHWORD_H

#include "Hash.h"
#include <iostream>

using namespace std;
// hash-funktionen summerar
// ascii värdet på alla tecken i ordet det ger ett
// så unikt returvärde om det inte finns en dublett
// däremot  när värdet sen delas med tablesize och
// trunkeras till int kan det hända att 2
// värden får samma hashkey
class EnglishWord
{
private:
    string word;
public:
	EnglishWord* next;
	EnglishWord(string word){
	 this->word=word;
	 next = nullptr;
	}
	EnglishWord(){
		this->word="";
	  next = nullptr;
	}
	EnglishWord(const EnglishWord& word){
		this->word = word.word;
		next =nullptr;
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
        string word = eng.getValue();
         int value =0;
		 for (unsigned int i = 0; i < word.length(); ++i) {
              value += word[i];
		 }
        return value;
	}
};

#endif
