package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button userVar;
    private Button organizerVar;
    private Button userLogOn;
    private Button organizerLogOn;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userVar=(Button) findViewById(R.id.user);
        organizerVar=(Button) findViewById(R.id.organizer);
        userLogOn=(Button)findViewById(R.id.signUpAsUsr);
        organizerLogOn=(Button)findViewById(R.id.signUpAsOrg);

        userVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,userLogin.class);
                startActivity(intent);
            }
        });

        organizerVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,organizerLogin.class);
                startActivity(intent);
            }
        });

        userLogOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserRegistration.class);
                startActivity(intent);
            }
        });

        organizerLogOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OrganizerRegistration.class);
                startActivity(intent);
            }
        });


    }
}
