package com.gamera.gamera.items;

public class armor implements item{
    int id;
    String type; // head piece || chest piece || arm piece || hand piece || pants || shoes
    String name;

    public armor(String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
