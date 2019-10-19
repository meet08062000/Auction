package com.example.auction;

import java.util.ArrayList;

class Organizer
{
    private String fname,lname,uid;
    ArrayList<User> usersList = new ArrayList<>();
    void adduser(String fname,String lname,String uid)
    {

        User temp = new User(fname,lname,uid);
        usersList.add(temp);

    }
}
