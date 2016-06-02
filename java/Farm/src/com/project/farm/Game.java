package com.project.farm;

import java.io.File;

public class Game {
    private SoundPlayer play;
    private Animal randomAnimal;
    private Farm farm;
    private int highScore;
    
    Game(){
        farm = new Farm();
        play = new SoundPlayer();
        randomAnimal = farm.randomAnimal();
        highScore=0;
    }

    Game(int highscore){
        farm = new Farm();
        play = new SoundPlayer();
        this.highScore=highscore;
        randomAnimal = farm.randomAnimal();
    }
    
    public void setHighscore(int highscore){
        this.highScore=highscore;
    }

    public void readHighscore(File file){
       highScore = FileManagement.readFromFile(file);
    }

    public void saveHighscore(File file){
        FileManagement.saveTofile(file,highScore);
    }

    private void addscore(){
        this.highScore++;
    }

    public int getHighscore(){
        return highScore;
    }

    public void playSound(){
      play.play(randomAnimal.sound);
    }
    public void stopSound(){
        play.stop();
    }
    public boolean animalclicked(String animal){
        boolean rightAnimal=false;
        if(animal == randomAnimal.animal){
            addscore();
            rightAnimal=true;
            randomAnimal = farm.randomAnimal();
       }
       return rightAnimal;
    }
}
