package com.example.investoready;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    RelativeLayout login;
    TextView signup_inlogin, forgotpassword;
    EditText loginid, loginpassword;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        login = findViewById(R.id.login);
        signup_inlogin = findViewById(R.id.signup_inlogin);
        forgotpassword = findViewById(R.id.forgotpasswordlogin);
        loginid = findViewById(R.id.loginid);
        loginpassword = findViewById(R.id.loginpassword);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();  //take instance of user to login

//        if(firebaseUser!=null)
//        {
//            finish();
//            Intent intent = new Intent(LogIn.this, Home.class);
//            startActivity(intent);
//        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = loginid.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();

                if(mail.isEmpty()||password.isEmpty())
                    Toast.makeText(LogIn.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                else
                {
                    //login the user
                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                checkmailverification(mail);
                            }
                            else
                            {
                                Toast.makeText(LogIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signup_inlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
    private void checkmailverification(String mail1)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(LogIn.this, Home.class);
            intent.putExtra("email", mail1);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Verify your mail first!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}