package com.project.farm;


import javax.swing.*;

public abstract class Animals {
    protected String name;
    protected String sound;
    protected String animal;
    public JButton button;
    Animals(){

    }
    public String getname(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }


}
