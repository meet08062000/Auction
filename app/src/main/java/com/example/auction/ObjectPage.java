package com.example.auction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ObjectPage extends AppCompatActivity {

    private TextView name;
    private TextView desc;
    private TextView curr;
    private TextView leader;
    private Button bid;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private int userBid;
    private String obj,auc;
    private ObjectToBeSold currobj;

    private static final String TAG = "ObjectPage";

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
        leader = (TextView)findViewById(R.id.user);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        db.collection("Objects").document(obj).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                setcurrObj(documentSnapshot);

                name.setText("Object name: "+currobj.name);
                desc.setText(currobj.desc);
                curr.setText("Current bid: "+currobj.currBid);

                if(currobj.user!="")
                {
                    db.collection("Users").document(currobj.user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User u = documentSnapshot.toObject(User.class);
                            String uname = u.fname+" "+u.lname;

                            leader.setText("Current highest bidder: "+uname);
                        }
                    });
                }
            }
        });

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(ObjectPage.this);
                View promptView = layoutInflater.inflate(R.layout.user_bid, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(ObjectPage.this);
                alert.setView(promptView);
                final EditText newBid = (EditText)promptView.findViewById(R.id.new_bid);

                alert.setTitle("Enter your bid");

                alert.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int n = Integer.parseInt(newBid.getText().toString());

                                if(n>currobj.currBid)
                                {
                                    currobj.currBid = n;
                                    currobj.user = firebaseAuth.getCurrentUser().getUid();

                                    db.collection("Users").document(currobj.user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            User u = documentSnapshot.toObject(User.class);
                                            String uname = u.fname+" "+u.lname;

                                            leader.setText("Current highest bidder: "+uname);
                                        }
                                    });

                                    curr.setText("Current bid : "+ currobj.currBid);
                                    db.collection("Objects").document(currobj.name).set(currobj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: majaa aavi gayi baapu!!");

                                        }
                                    });

                                    db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                                            {
                                                User u = documentSnapshot.toObject(User.class);

                                                if(u.wishlist.contains(currobj.name))
                                                {
                                                    u.wishlist.remove(currobj.name);
                                                    db.collection("Users").document(documentSnapshot.getId()).set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "onSuccess: update ho gaya");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d(TAG, "onFailure: some error");
                                                        }
                                                    });
                                                }


                                            }

                                            db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    User us = documentSnapshot.toObject(User.class);

                                                    Log.d(TAG, "onSuccess: here");

                                                    us.wishlist.add(obj);
                                                    db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).set(us).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "onSuccess: user updated");
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFailure: failed!!!!!!");
                                                }
                                            });
                                        }
                                    });


                                }

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.create();
                alert.show();

            }
        });
    }

    void setcurrObj(DocumentSnapshot documentSnapshot)
    {
        currobj = documentSnapshot.toObject(ObjectToBeSold.class);
    }
}
