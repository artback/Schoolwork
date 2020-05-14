package com.project.farm;

import java.util.Random;
import java.util.Vector;

public class Farm {
    private Vector<Animal> animal;

    public Farm(){
        animal = new Vector<>();
        animal.add(new Pig("Pig","/home/freak/Music/pig.wav"));
        animal.add(new Cow("Cow","/home/freak/Music/cow.wav"));
        animal.add(new Dog("Dog","/home/freak/Music/dog.wav"));
        animal.add(new Hen("Hen","/home/freak/Music/hen.wav"));
        animal.add(new Sheep("Sheep","/home/freak/Music/sheep.wav"));
    }

    public Animal randomAnimal(){
        Random rand = new Random();
        int value = rand.nextInt(animal.size());
        return animal.elementAt(value);
    }

    public Vector<String> animalStrings(){
        Vector<String> vec = new Vector<>();

        for (int i = 0; i < animal.size(); i++) {
           vec.add(animal.elementAt(i).animal);
        }

        return vec;
    }
}

