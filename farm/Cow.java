package com.project.farm;


public class Cow extends Animal {
    private String id;
    Cow(String animal,String sound){
        super(animal,sound);
        this.id="JL431";
    }
    public String getID(){
        return id;
    }

}

