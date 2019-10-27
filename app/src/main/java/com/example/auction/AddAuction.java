package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddAuction extends AppCompatActivity {

    private EditText objectType;
    private EditText description;
    private EditText addAuction;
    private Button addObject;
    private Button goBack;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private Organizer organizer;
    private Auction auction;
    private DocumentReference docref;
    private static final String TAG = "AddAuction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);

        Log.d(TAG, "onCreate: entered addauction class");

        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        docref=db.collection("Organizers").document(firebaseAuth.getCurrentUser().getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess: user mil gaya");
                organizer = documentSnapshot.toObject(Organizer.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: user hi nai mila to aage kaisa jaayega");
            }
        });

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
                Log.d(TAG, "onClick: clicked");
                if(objectType.getText().toString().isEmpty()||addAuction.getText().toString().isEmpty()||description.getText().toString().isEmpty())
                {
                    Toast.makeText(AddAuction.this,"Please enter all the fields.",Toast.LENGTH_SHORT).show();
                    return;
                }
                auction = new Auction(addAuction.getText().toString(),organizer);
                organizer.auctions.add(auction);
                docref.update("auctions",organizer.auctions)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: yippee!!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: faltugiri!!");
                            }
                        });
            }
        });

    }
}
