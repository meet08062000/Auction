package com.example.auction;

import java.util.ArrayList;
import java.util.List;

class Auction
{
    public String name;
    public List<ObjectToBeSold> items= new ArrayList<>();
    public Organizer organizer = new Organizer();

    /*public Auction(String name, List<ObjectToBeSold> items, Organizer organizer) {
        this.name = name;
        this.items = items;
        this.organizer = organizer;
    }*/
}
