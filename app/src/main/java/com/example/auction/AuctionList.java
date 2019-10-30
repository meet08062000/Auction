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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AuctionList extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> auclist= new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "AuctionList";

    public static final String EXTRA_AUCTION = "com.example.auction.EXTRA_AUCTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_list);

        list = (ListView)findViewById(R.id.auction_list);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        db.collection("Auctions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc: task.getResult())
                    {
                        auclist.add(doc.getId());
                    }
                    Log.d(TAG, "onComplete: aaaaa "+auclist.toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuctionList.this,android.R.layout.simple_list_item_1,auclist);
                    list.setAdapter(adapter);

                }
                else
                {
                    Log.d(TAG, "onComplete: failed");
                }
                Log.d(TAG, "onComplete: exec completed");
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedAuction = list.getItemAtPosition(position).toString();
                Intent intent = new Intent(AuctionList.this, UserAuctionPage.class);
                intent.putExtra(EXTRA_AUCTION,clickedAuction);
                startActivity(intent);
            }
        });

    }
}
