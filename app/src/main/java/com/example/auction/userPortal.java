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

public class userPortal extends AppCompatActivity {

    private ListView usrList;
    private ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        usrList = (ListView)findViewById(R.id.userList);

        names.add("All auctions");
        names.add("Your auctions");
        names.add("Logout");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        usrList.setAdapter(adapter);

        usrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {

                }
                else if(position==1)
                {

                }
                else if(position==2)
                {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(userPortal.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
