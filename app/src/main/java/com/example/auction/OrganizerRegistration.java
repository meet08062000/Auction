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

public class OrganizerRegistration extends AppCompatActivity {

    private EditText orgFname;
    private EditText orgLname;
    private EditText organizerUid;
    private EditText orgPw;
    private EditText orgRetype;
    private Button ogSubmit;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_registration);

        orgFname=(EditText)findViewById(R.id.firstName);
        orgLname=(EditText)findViewById(R.id.lastName);
        organizerUid=(EditText)findViewById(R.id.ogUid);
        orgPw= (EditText)findViewById(R.id.newPw);
        orgRetype=(EditText)findViewById(R.id.retype);
        ogSubmit=(Button)findViewById(R.id.orgSubmit);

        ogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeOrg(orgFname.getText().toString(),orgLname.getText().toString(),organizerUid.getText().toString(),orgPw.getText().toString(),orgRetype.getText().toString());
            }
        });

    }

    private void storeOrg(String fname,String lname,String uid,String pw,String retype)
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

            firebaseAuth= FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(uid,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(OrganizerRegistration.this, "Registration successful!!", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(OrganizerRegistration.this, "Registration failed, please try again.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            Intent intent = new Intent(OrganizerRegistration.this,MainActivity.class);
            finish();
            startActivity(intent);

        }

        else
        {

            Toast.makeText(getApplicationContext(),"Your retyped password does not match your original password",Toast.LENGTH_LONG).show();

        }

    }

}
