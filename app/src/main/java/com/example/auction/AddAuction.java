package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddAuction extends AppCompatActivity {

    private EditText addAuction;
    private EditText objectType;
    private EditText description;
    private Button addObject;
    private Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);

        addAuction = (EditText)findViewById(R.id.AuctionName);
        objectType = (EditText)findViewById(R.id.ObjectType);
        description = (EditText)findViewById(R.id.auction_description);
        addObject = (Button)findViewById(R.id.AddObject);
        goBack = (Button)findViewById(R.id.AddGoBack);

        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAuction.this,AddObject.class);
                startActivity(intent);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
