package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class organizerLogin extends AppCompatActivity {

    private EditText orgName;
    private EditText orgpswd;
    private int count=5;
    private Button log;
    private TextView text;
    private TextView ogSignUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private static final String TAG = "organizerLogin";

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
        db = FirebaseFirestore.getInstance();

        final FirebaseUser organizer = firebaseAuth.getCurrentUser();

        if(organizer!=null)
        {
            db.collection("Organizers").document(organizer.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists())
                    {
                        boolean emailverify = checkEmailVerification();
                        if(emailverify)
                        {
                            finish();
                            startActivity(new Intent(organizerLogin.this,organizerPortal.class));
                        }
                        else
                        {
                            firebaseAuth.signOut();
                        }
                    }
                    else
                    {
                        Toast.makeText(organizerLogin.this, "You are signed in as a user. You cannot login as organizer.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(organizerLogin.this, MainActivity.class));
                    }
                }
            });

        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(orgName.getText().toString(),orgpswd.getText().toString());
                Log.d(TAG, "onClick: clicked");
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
        Log.d(TAG, "validate: in validate");
        firebaseAuth.signInWithEmailAndPassword(uname, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Log.d(TAG, "onComplete: signed in");
                    db.collection("Organizers").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if(documentSnapshot.exists())
                            {
                                checkEmailVerification();
                            }
                            else
                            {
                                firebaseAuth.signOut();
                                Toast.makeText(organizerLogin.this, "You are a user. You cannot login as organizer.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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

    private boolean checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            Toast.makeText(organizerLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(organizerLogin.this,organizerPortal.class));
            finish();
            return true;
        }
        else
        {
            Toast.makeText(this,"Verify your Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            return false;
        }
    }

}
