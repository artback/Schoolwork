package com.project.farm;


import javax.swing.*;

public class Dog extends Animals{
     Dog(){
         this.name="Pluto";
         this.animal="dog";
         this.button= new JButton(this.animal);
         this.sound = "/home/freak/Music/dog.wav";
    }
}
