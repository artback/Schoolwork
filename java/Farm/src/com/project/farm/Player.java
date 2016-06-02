package com.project.farm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javafx.scene.media.AudioClip;

public class Player {
    public JButton button;
    private AudioClip clip;
    public Player(){
        button = new JButton("PLAY");
        playAction();
    }
    public  Player(String fileName) {
        button = new JButton("PLAY");
        playAction();
        this.clip = new AudioClip(new File(fileName).toURI().toString());
    }
    public void setSound(String fileName){
        this.clip = new AudioClip(new File(fileName).toURI().toString());
    }
    public void play(String fileName){
        this.clip = new AudioClip(new File(fileName).toURI().toString());
        play();
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
    public void playAction(String text){
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                play();
                button.setText(text);
            }});
    }
}

