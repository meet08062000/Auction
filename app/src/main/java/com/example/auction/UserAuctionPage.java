package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserAuctionPage extends AppCompatActivity {

    private static final String TAG = "UserAuctionPage";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private ArrayList<String> objlist = new ArrayList<>();
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auction_page);

        Intent intent = getIntent();
        String auc = intent.getStringExtra(AuctionList.EXTRA_AUCTION);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        list = (ListView)findViewById(R.id.object_list);

        db.collection("Auctions").document(auc).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Auction auct = documentSnapshot.toObject(Auction.class);
                objlist = (ArrayList<String>) auct.getObj();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserAuctionPage.this, android.R.layout.simple_list_item_1, objlist);
                list.setAdapter(adapter);
                Log.d(TAG, "onSuccess: bbbbb "+objlist.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: fail fail fail!!!");
            }
        });

    }
}
