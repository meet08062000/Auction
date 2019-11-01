package com.example.auction;

import java.util.ArrayList;
import java.util.List;

class Auction
{
    public String name;
    public String organizer;
    public String desc;
    public String type;
    public boolean on;
    public List<String> obj;

    public Auction() {
        name="";
        organizer="";
        desc="";
        type="";
        obj=new ArrayList<>();
        on=false;
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

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public List<String> getObj() {
        return obj;
    }

    public void setObj(List<String> obj) {
        this.obj = obj;
    }

}
