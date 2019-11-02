package com.example.auction;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WonList extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> wonOb = new ArrayList<String>();
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private static final String TAG = "WonList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_list);

        list = (ListView)findViewById(R.id.objects_won);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                User u = documentSnapshot.toObject(User.class);

                wonOb = (ArrayList<String>) u.won;

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(WonList.this, android.R.layout.simple_list_item_1, wonOb);
                list.setAdapter(adapter);
            }
        });
    }
}
