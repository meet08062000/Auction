package com.example.auction;

import java.util.ArrayList;
import java.util.List;

class Organizer {
    public String fname,lname,email;
    public List<Auction> auctions = new ArrayList<>();

    public Organizer(String fname, String lname, String email) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }
}
