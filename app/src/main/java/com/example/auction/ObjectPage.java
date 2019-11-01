package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ObjectPage extends AppCompatActivity {

    private TextView name;
    private TextView desc;
    private TextView curr;
    private Button bid;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private int userBid;
    private String obj,auc;
    private ObjectToBeSold currobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_page);

        Intent intent = getIntent();
        obj = intent.getStringExtra(UserAuctionPage.EXTRA_OBJ);
        auc = intent.getStringExtra(UserAuctionPage.EXTRA_OBJ_AUCTION);

        name = (TextView)findViewById(R.id.object_name);
        desc = (TextView)findViewById(R.id.object_description);
        curr = (TextView)findViewById(R.id.object_current_bid);
        bid = (Button) findViewById(R.id.user_input_bid);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        db.collection("Objects").document(obj).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currobj = documentSnapshot.toObject(ObjectToBeSold.class);

                name.setText("Object name: "+currobj.name);
                desc.setText(currobj.desc);
                curr.setText("Current bid: "+currobj.currBid);
            }
        });

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
