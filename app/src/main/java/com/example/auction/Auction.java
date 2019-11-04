package com.example.auction;

import java.util.List;

class Auction
{
    public String name;
    public String organizer;
    public String desc;
    public String type;
    public List<String> obj;

    public Auction() {
    }

    public Auction(String name, String organizer, String desc, String type, List<String> obj) {
        this.name = name;
        this.organizer = organizer;
        this.desc = desc;
        this.type = type;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getObj() {
        return obj;
    }

}
