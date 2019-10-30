package com.example.auction;

import java.util.ArrayList;
import java.util.List;

class ObjectToBeSold
{
    public String auction;
    public String name;
    public String desc;
    public int startBid;
    public int currBid;
    public List<String> userList = new ArrayList<>();

    public ObjectToBeSold(String suction, String name, String desc, int startBid, List<String> userList) {
        this.auction = auction;
        this.name = name;
        this.desc = desc;
        this.startBid = startBid;
        this.currBid = startBid;
        this.userList = userList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartBid() {
        return startBid;
    }

    public void setStartBid(int startBid) {
        this.startBid = startBid;
    }

    public int getCurrBid() {
        return currBid;
    }

    public void setCurrBid(int currBid) {
        this.currBid = currBid;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
