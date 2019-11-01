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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrganizerRegistration extends AppCompatActivity {

    private static final String TAG = "OrganizerRegistration";

    private EditText orgFname;
    private EditText orgLname;
    private EditText organizerUid;
    private EditText orgPw;
    private EditText orgRetype;
    private Button ogSubmit;
    private FirebaseAuth firebaseAuth;
    private String fname,lname,email;
    private FirebaseFirestore db;
    private CollectionReference colref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_registration);
        
        db = FirebaseFirestore.getInstance();
        colref = db.collection("Organizers");

        orgFname=(EditText)findViewById(R.id.firstName);
        orgLname=(EditText)findViewById(R.id.lastName);
        organizerUid=(EditText)findViewById(R.id.ogUid);
        orgPw= (EditText)findViewById(R.id.newPw);
        orgRetype=(EditText)findViewById(R.id.retype);
        ogSubmit=(Button)findViewById(R.id.orgSubmit);

        ogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: CLiccked");
                storeOrg(orgPw.getText().toString(),orgRetype.getText().toString());
            }
        });

    }

    private void storeOrg(String pw,String retype)
    {
        fname=orgFname.getText().toString();
        lname=orgLname.getText().toString();
        email=organizerUid.getText().toString();
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
        else if(email.isEmpty())
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
            Log.d(TAG, "storeOrg: IN lAst");
            firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "onComplete: AA = "+task.getResult().toString());
                    if(task.isSuccessful())
                    {
                        Log.d(TAG, "onComplete: Success in Verification");
                        sendEmailVerification();
                    }
                    else
                    {
                        Toast.makeText(OrganizerRegistration.this, "Registration failed, please try again.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onComplete: ", task.getException());
                        finish();
                    }
                }
            });

        }

        else
        {
            Toast.makeText(OrganizerRegistration.this,"Your retyped password does not match your original password",Toast.LENGTH_LONG).show();
        }

    }

    private void sendEmailVerification()
    {
        Log.d(TAG, "sendEmailVerification: In HEre");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            Log.d(TAG, "sendEmailVerification: UID = "+firebaseUser.getUid());
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d(TAG, "sendEmailVerification onComplete: Called ");
                        sendUserData();
                        Toast.makeText(OrganizerRegistration.this,"Successfully Registered, please verify your email",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(OrganizerRegistration.this,"Verification mail has not been sent, please check your internet connection.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else{
            Log.d(TAG, "sendEmailVerification: NULL USER");
        }
    }

    private void sendUserData()
    {
        Log.d(TAG, "sendUserData: FName = "+fname);
        Log.d(TAG, "sendUserData: LName = "+lname);
        Log.d(TAG, "sendUserData: Email = "+email);
        Log.d(TAG, "sendUserData: UID = "+firebaseAuth.getCurrentUser().getUid());

        Organizer org = new Organizer(fname, lname, email, new ArrayList<String>());

        Log.d(TAG, "sendUserData: iske andar aa raha hai"+org.fname+org.lname+org.email);

        db.collection("Organizers").document(firebaseAuth.getCurrentUser().getUid()).set(org)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: majaa aa gaya");
                        Toast.makeText(OrganizerRegistration.this, "It's Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrganizerRegistration.this,organizerLogin.class);
                        finish();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: so near yet so far");
                    }
                });
    }

}
