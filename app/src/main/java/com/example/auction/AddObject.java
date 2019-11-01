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

public class AddObject extends AppCompatActivity {

    private EditText name;
    private EditText desc;
    private EditText startBid;
    private Button add;
    private Button finishAdding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private DocumentReference docref,docref2;
    private String objName, objDesc, auction;
    private ObjectToBeSold obj;
    private int start;
    private Auction auc;
    private static final String TAG = "AddObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);

        Intent intent = getIntent();
        auction = intent.getStringExtra(AddAuction.EXTRA_TEXT);

        name = (EditText)findViewById(R.id.object_name);
        desc = (EditText)findViewById(R.id.object_description);
        startBid = (EditText)findViewById(R.id.start_bid);
        add = (Button)findViewById(R.id.add_object);
        finishAdding = (Button)findViewById(R.id.finish);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        docref = db.collection("Auctions").document(auction);
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess: found the auction");
                auc = documentSnapshot.toObject(Auction.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: auction naa malyu");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                objName = name.getText().toString();
                objDesc = desc.getText().toString();
                start = Integer.parseInt(startBid.getText().toString());
                if(objName.isEmpty()||objDesc.isEmpty()||startBid.getText().toString().isEmpty())
                {
                    Toast.makeText(AddObject.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                obj = new ObjectToBeSold(auction, objName, objDesc,start);
                Log.d(TAG, "onCreate: "+auction);
                docref2 = db.collection("Objects").document(objName);
                docref2.set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Object dic formed");
                    }
                });

                auc.obj.add(objName);
                docref.update("obj",auc.obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: itni khushi!!");
                    }
                });

                Toast.makeText(AddObject.this, "Object added successfully", Toast.LENGTH_SHORT);

                name.setText("");
                desc.setText("");
                startBid.setText("");
            }
        });

        finishAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AddObject.this, organizerPortal.class));
            }
        });

    }
}
