package com.gamera.gamera.items;

import com.gamera.gamera.items.item;

public class food implements item {
    int id;
    String type; // forage | vegetable | fruit
    String name;
    double growth = 0;
    double freshness = 1;
    double quality = 1;
    double base_price;

    public food(String type,String name, double base_price){
        this.type = type;
        this.name = name;
        this.base_price = base_price;
    }

    public String getName(){
        return name;
    }

}
