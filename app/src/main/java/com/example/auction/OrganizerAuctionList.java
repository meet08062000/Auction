package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrganizerAuctionList extends AppCompatActivity {

    private ListView list;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private DocumentReference docref;

    private static final String TAG = "OrganizerAuctionList";

    public static final String EXTRA_ORGANIZER_AUCTION = "com.example.auction.EXTRA_ORGANIZER_AUCTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_auction_list);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        docref = db.collection("Organizers").document(firebaseAuth.getCurrentUser().getUid());

        list = (ListView)findViewById(R.id.org_auc_list);
        Log.d(TAG, "onCreate: aaaaaaaa"+firebaseAuth.getCurrentUser().getUid());
        
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Organizer org = documentSnapshot.toObject(Organizer.class);
                Log.d(TAG, "onSuccess: orggainzer.uction"+org.auctions.toString());
                ArrayList<String> oblist;
                oblist = (ArrayList<String>) org.auctions;
                Log.d(TAG, "onSuccess: tptptptp");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrganizerAuctionList.this, android.R.layout.simple_list_item_1, oblist);
                list.setAdapter(adapter);

                Log.d(TAG, "onSuccess: jeet gaya!!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: tatti");
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = list.getItemAtPosition(position).toString();

                Intent intent = new Intent(OrganizerAuctionList.this, OrganizerObjectList.class);
                intent.putExtra(EXTRA_ORGANIZER_AUCTION, a);
                startActivity(intent);
            }
        });
    }
}
