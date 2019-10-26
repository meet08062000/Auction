package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddAuction extends AppCompatActivity {

    private EditText objectType;
    private EditText description;
    private EditText addAuction;
    private Button addObject;
    private Button goBack;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private List<Auction> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);

        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

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
