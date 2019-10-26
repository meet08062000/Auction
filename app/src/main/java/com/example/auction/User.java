package com.example.auction;

import java.util.ArrayList;
import java.util.List;

class User {
    public String fname,lname,email;
    public List<ObjectToBeSold> wishlist = new ArrayList<>();

    public User(String fname, String lname, String email) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }
}
