package com.project.farm;

public class Hen extends Animal {
    int nrOfEggs;
    String colorOfEggs;

        Hen(String animal,String sound){
            super(animal,sound);
            nrOfEggs=0;
            colorOfEggs="White";
    }

}
