package com.example.auction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChooseAuctionToBeDeleted extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> auclist = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private Organizer org;
    private String s1;

    private static final String TAG = "ChooseAuctionToBeDelete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auction_to_be_deleted);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        list = (ListView)findViewById(R.id.auc_list_to_be_removed);

        db.collection("Organizers").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setOrg(documentSnapshot);

                auclist = (ArrayList<String>) org.auctions;

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChooseAuctionToBeDeleted.this, android.R.layout.simple_list_item_1, auclist);
                list.setAdapter(adapter);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String clickedAuction = list.getItemAtPosition(position).toString();
                
                db.collection("Auctions").document(clickedAuction).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Auction a = documentSnapshot.toObject(Auction.class);
                        
                        for(String s: a.obj)
                        {
                            s1 = s;
                            Log.d(TAG, "onSuccess:  s = "+s);
                            db.collection("Objects").document(s).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: object deleted");
                                }
                            });

                            db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                    {
                                        User u = documentSnapshot.toObject(User.class);

                                        if(u.wishlist.contains(s1))
                                        {
                                            u.wishlist.remove(s1);
                                            u.won.add(s1);

                                            Log.d(TAG, "onSuccess: s1 = "+s1);
                                            db.collection("Users").document(documentSnapshot.getId()).set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "onSuccess: the whole app is complete");
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }

                        db.collection("Auctions").document(clickedAuction).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                org.auctions.remove(clickedAuction);

                                db.collection("Organizers").document(firebaseAuth.getCurrentUser().getUid()).set(org).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: ok");
                                    }
                                });
                            }
                        });

                        Toast.makeText(ChooseAuctionToBeDeleted.this, "Delete successful.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
            }
        });
    }

    private void setOrg(DocumentSnapshot documentSnapshot)
    {
        org = documentSnapshot.toObject(Organizer.class);
    }
}
