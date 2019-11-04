package com.example.auction;

class ObjectToBeSold
{
    public String auction;
    public String name;
    public String desc;
    public int startBid;
    public int currBid;
    public String user;

    public ObjectToBeSold() {
    }

    public ObjectToBeSold(String auction, String name, String desc, int startBid) {
        this.auction = auction;
        this.name = name;
        this.desc = desc;
        this.startBid = startBid;
        this.currBid = startBid;
        user = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
