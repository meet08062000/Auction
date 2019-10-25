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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userLogin extends AppCompatActivity {

    private EditText name;
    private EditText passwd;
    private TextView noAttempts;
    private Button login;
    private int ctr=5;
    private TextView usSignUp;
    private FirebaseAuth firebaseAuth;

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

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null)
        {
            finish();
            startActivity(new Intent(userLogin.this,userPortal.class));
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
                    checkEmailVerification();
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

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag)
        {
            Toast.makeText(userLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(userLogin.this,userPortal.class));
        }
        else
        {
            Toast.makeText(this,"Verify your Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}
