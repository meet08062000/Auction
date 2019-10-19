package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserRegistration extends AppCompatActivity {

    private EditText usFname;
    private EditText usLname;
    private EditText userUid;
    private EditText usPswd;
    private EditText retypeUs;
    private Button usSubmit;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        usFname=(EditText)findViewById(R.id.usrFirstName);
        usLname=(EditText)findViewById(R.id.usrLastName);
        userUid=(EditText)findViewById(R.id.usUid);
        usPswd=(EditText)findViewById(R.id.usrPasswd);
        retypeUs=(EditText)findViewById(R.id.userRetype);
        usSubmit=(Button)findViewById(R.id.usrSubmit);

        usSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeUser(usFname.getText().toString(),usLname.getText().toString(),userUid.getText().toString(),usPswd.getText().toString(),retypeUs.getText().toString());
            }
        });

    }

    private void storeUser(String fname,String lname,String uid,String pw,String retype)
    {
        if(fname.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"First name cannot be empty.",Toast.LENGTH_LONG).show();
            return;
        }
        else if(lname.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Last name cannot be empty.",Toast.LENGTH_LONG).show();
            return;
        }
        else if(uid.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"UserName cannot be empty.",Toast.LENGTH_LONG).show();
            return;
        }
        else if(pw.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Password cannot be empty.",Toast.LENGTH_LONG).show();
            return;
        }
        else if(retype.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Retype password cannot be empty.",Toast.LENGTH_LONG).show();
            return;
        }

        if(pw.equals(retype))
        {

            firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(uid,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(UserRegistration.this, "Registration successful!!", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(UserRegistration.this, "Registration failed, please try again.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });

            Intent intent = new Intent(UserRegistration.this,MainActivity.class);
            finish();
            startActivity(intent);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Your retyped password does not match your original password",Toast.LENGTH_LONG).show();
        }

    }
}
