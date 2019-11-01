package com.example.auction;

import android.content.Intent;
import android.os.Bundle;
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

public class userLogin extends AppCompatActivity {

    private EditText name;
    private EditText passwd;
    private TextView noAttempts;
    private Button login;
    private int ctr=5;
    private TextView usSignUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login2);

        name=(EditText)findViewById(R.id.username);
        passwd=(EditText)findViewById(R.id.userPasswd);
        noAttempts=(TextView)findViewById(R.id.attempts);
        login=(Button)findViewById(R.id.loginAsUser);
        usSignUp=(TextView)findViewById(R.id.userSignUp);

        noAttempts.setText("No of attempts remaining: "+ctr);

        firebaseAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null)
        {
            db.collection("Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists())
                    {
                        boolean emailverify = checkEmailVerification();
                        if(emailverify)
                        {
                            finish();
                            startActivity(new Intent(userLogin.this,userPortal.class));
                        }
                        else
                        {
                            firebaseAuth.signOut();
                        }
                    }
                    else
                    {
                        Toast.makeText(userLogin.this, "You are signed in as a organizer. You cannot login as user.", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(userLogin.this, MainActivity.class));
                    }
                }
            });

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(name.getText().toString(),passwd.getText().toString());
            }
        });

        usSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userLogin.this,UserRegistration.class);
                startActivity(intent);
            }
        });

    }

    private void check(String uname, String pwd) {

        firebaseAuth.signInWithEmailAndPassword(uname, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists())
                            {
                                checkEmailVerification();
                            }
                            else
                            {
                                firebaseAuth.signOut();
                                Toast.makeText(userLogin.this, "You are an organizer. You cannot login as user.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(userLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    ctr--;
                    if (ctr == 0)
                    {
                        login.setEnabled(false);
                    }
                    noAttempts.setText("No of attempts remaining: "+ctr);
                }
            }
        });

    }

    private boolean checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag)
        {
            Toast.makeText(userLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(userLogin.this,userPortal.class));
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
