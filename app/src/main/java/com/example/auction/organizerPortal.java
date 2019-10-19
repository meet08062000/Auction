package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class organizerPortal extends AppCompatActivity {

    private ListView orgList;
    private ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_portal);

        orgList = (ListView)findViewById(R.id.organizerList);

        names.add("Add a user");
        names.add("Remove a user");
        names.add("Auctions");
        names.add("Add an auction");
        names.add("Remove an auction");
        names.add("Logout");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        orgList.setAdapter(adapter);

        orgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent intent = new Intent(organizerPortal.this,UserRegistration.class);
                    startActivity(intent);
                }
                else if(position==1)
                {

                }
                else if(position==2)
                {

                }
                else if(position==3)
                {

                }
                else if(position==4)
                {

                }
                else if(position==5)
                {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(organizerPortal.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}