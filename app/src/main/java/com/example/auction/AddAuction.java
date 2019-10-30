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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddAuction extends AppCompatActivity {

    private EditText objectType;
    private EditText description;
    private EditText addAuction;
    private Button addObject;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private Organizer organizer;
    private Auction auction = new Auction();
    private DocumentReference docref;
    private DocumentReference docref2;
    private static final String TAG = "AddAuction";
    private ObjectToBeSold obj;
    private String name,type,desc;
    private String start, end;

    public static final String EXTRA_TEXT = "com.example.auction.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);

        Log.d(TAG, "onCreate: entered add auction class");

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

        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = addAuction.getText().toString();
                desc = description.getText().toString();
                type = objectType.getText().toString();
                if (name.isEmpty() || desc.isEmpty() || type.isEmpty()) {
                    Toast.makeText(AddAuction.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                add();
                finish();
                Intent intent = new Intent(AddAuction.this, AddObject.class);
                intent.putExtra(EXTRA_TEXT,name);
                startActivity(intent);
            }
        });

    }

    private void add()
    {
        auction = new Auction(name,firebaseAuth.getCurrentUser().getUid(),desc,type,new ArrayList<String>());
        docref2 = db.collection("Auctions").document(name);
        docref2.set(auction).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: ho gaya");
            }
        });

        organizer.auctions.add(name);
        docref.update("auctions",organizer.auctions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: ye ho jaaye to bhagwan mil jaaye");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: chal teri to");
            }
        });
    }

}
