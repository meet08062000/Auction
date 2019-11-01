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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegistration extends AppCompatActivity {

    private static final String TAG = "UserRegistration";
    private EditText usFname;
    private EditText usLname;
    private EditText userUid;
    private EditText usPswd;
    private EditText retypeUs;
    private Button usSubmit;
    private FirebaseAuth firebaseAuth;
    private String fname,lname,email;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        db = FirebaseFirestore.getInstance();

        usFname=(EditText)findViewById(R.id.usrFirstName);
        usLname=(EditText)findViewById(R.id.usrLastName);
        userUid=(EditText)findViewById(R.id.usUid);
        usPswd=(EditText)findViewById(R.id.usrPasswd);
        retypeUs=(EditText)findViewById(R.id.userRetype);
        usSubmit=(Button)findViewById(R.id.usrSubmit);

        usSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=usFname.getText().toString();
                lname=usLname.getText().toString();
                email=userUid.getText().toString();
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
                        sendEmailVerification();
                    }
                    else
                    {
                        Toast.makeText(UserRegistration.this, "Registration failed, please try again.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Your retyped password does not match your original password",Toast.LENGTH_LONG).show();
        }

    }

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        sendUserData();
                        Toast.makeText(UserRegistration.this,"Successfully Registered, please verify your email",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(UserRegistration.this,"Verification mail has not been sent, please check your internet connection.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData()
    {
        Log.d(TAG, "called");
        User user = new User(fname,lname,email);

        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "sendUserData: In IT");
                Toast.makeText(UserRegistration.this, "Success!!!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(UserRegistration.this,userLogin.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: E = "+e.toString());
                Log.e(TAG, "onFailure: ",e );
            }
        });
    }

}
