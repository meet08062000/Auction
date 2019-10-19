package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class organizerLogin extends AppCompatActivity {

    private EditText orgName;
    private EditText orgpswd;
    private int count=5;
    private Button log;
    private TextView text;
    private TextView ogSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_login);

        orgName=(EditText)findViewById(R.id.ogname);
        orgpswd=(EditText)findViewById(R.id.ogPw);
        log=(Button)findViewById(R.id.orglog);
        text=(TextView)findViewById(R.id.orgAtt);
        ogSignUp=(TextView)findViewById(R.id.orgSignUp);

        text.setText("No of attempts remaining: "+count);

        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser organizer = firebaseAuth.getCurrentUser();

        if(organizer!=null)
        {
            finish();
            startActivity(new Intent(organizerLogin.this,organizerPortal.class));
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(orgName.getText().toString(),orgpswd.getText().toString());
            }
        });

        ogSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(organizerLogin.this,OrganizerRegistration.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String uname, String pwd) {

        firebaseAuth.signInWithEmailAndPassword(uname, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    finish();
                    startActivity(new Intent(organizerLogin.this, organizerPortal.class));
                    Toast.makeText(organizerLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(organizerLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    count--;
                    if (count == 0)
                    {
                        log.setEnabled(false);
                    }
                    text.setText("No of attempts remaining: "+count);
                }
            }
        });

    }


}
