package com.project.farm;

import java.io.File;
import javafx.scene.media.AudioClip;

public class SoundPlayer {
    private AudioClip clip;

    public SoundPlayer(){
        this.clip=null;
    }

    public  SoundPlayer(String fileName) {
        if(fileName != "") {
            this.clip = new AudioClip(new File(fileName).toURI().toString());
        }
    }

    public void play(String fileName){
        if(fileName != "") {
            this.clip = new AudioClip(new File(fileName).toURI().toString());
            play();
        }
    }

    public void play(){
        if(clip != null) {
            clip.play();
        }
    }

    public void stop(){
        if(clip != null) {
            clip.stop();
        }
    }

}

