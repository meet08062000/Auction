package com.example.auction;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class Organizer {

    private static final String TAG = "Organizer";

    public String fname,lname,email;
    public List<String> auctions;

    public Organizer() {
        fname = "";
        lname = "";
        email = "";
        auctions = new ArrayList<>();
    }

    public Organizer(String fname, String lname, String email, List<String> auctions) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.auctions = auctions;
        Log.d(TAG, "sendUserData: iske andar aa raha hai "+this.fname+this.lname+this.email);
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
