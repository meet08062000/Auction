package com.example.auction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddObject extends AppCompatActivity {

    private EditText name;
    private EditText desc;
    private EditText startBid;
    private EditText startDate;
    private EditText endDate;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);

        name = (EditText)findViewById(R.id.object_name);
        desc = (EditText)findViewById(R.id.object_description);
        startBid = (EditText)findViewById(R.id.start_bid);
        startDate = (EditText)findViewById(R.id.start_date);
        endDate = (EditText)findViewById(R.id.end_date);
        add = (Button)findViewById(R.id.add_object);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
