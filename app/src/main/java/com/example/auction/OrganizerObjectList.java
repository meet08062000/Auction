package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrganizerObjectList extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> oblist = new ArrayList<>();
    private String auc;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_object_list);

        Intent intent = getIntent();
        auc = intent.getStringExtra(OrganizerAuctionList.EXTRA_ORGANIZER_AUCTION);

        list = (ListView)findViewById(R.id.ob_list);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        db.collection("Auctions").document(auc).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Auction auction = documentSnapshot.toObject(Auction.class);
                oblist = (ArrayList<String>) auction.obj;

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrganizerObjectList.this, android.R.layout.simple_list_item_1, oblist);
                list.setAdapter(adapter);
            }
        });
    }
}
