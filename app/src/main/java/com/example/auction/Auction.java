package com.example.auction;

import java.util.ArrayList;
import java.util.List;

class Auction
{
    public String name;
    public List<ObjectToBeSold> items= new ArrayList<>();
    public Organizer organizer;

    public Auction(String name,Organizer organizer) {
        this.name = name;
        this.organizer = organizer;
    }
}
