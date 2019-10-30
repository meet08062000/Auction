package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
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

        names.add("Auctions");
        names.add("Add an auction");
        names.add("Remove an auction");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        orgList.setAdapter(adapter);

        orgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    //startActivity();
                }
                else if(position==1)
                {
                    Intent intent = new Intent(organizerPortal.this,AddAuction.class);
                    startActivity(intent);
                }
                else if(position==2)
                {

                }

            }
        });

    }

    private void Logout()
    {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(organizerPortal.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logoutMenu: {
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}