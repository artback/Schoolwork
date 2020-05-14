package com.project.farm;


public abstract class Animal {
    private String sound;
    private String animal;
    Animal(String animal,String sound){
        this.animal=animal;
        this.sound=sound;
    }
    public String getAnimal(){
        return animal;
    }
    public String getSound(){
        return sound;
    }

}
