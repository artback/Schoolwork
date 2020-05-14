package com.project.farm;



public class Dog extends Animal{
    private String name;
    Dog(String animal,String sound){
        super(animal,sound);
        this.name="Pluto";
    }
    public String getName(){
        return name;
    }
}
