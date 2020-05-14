package com.project.farm;


import javafx.scene.media.Media;

import javax.swing.*;

public class Cow extends Animals {
    Cow(){
        this.animal="cow";
        button = new JButton(this.animal);
        this.name="Kramer";
        this.sound = "/home/freak/Music/cow.wav";
    }

}

