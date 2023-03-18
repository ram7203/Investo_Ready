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

import com.example.investoready.Database.StockInfo;
import com.example.investoready.Database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {
    RelativeLayout recoverpassword;
    TextView login_resetpassword;
    EditText resetid, resetpassword;
    private FirebaseAuth firebaseAuth;
    List<User> userList;
    int value;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        recoverpassword = findViewById(R.id.recoverpassword);
        login_resetpassword = findViewById(R.id.login_resetpassword);
        resetid = findViewById(R.id.resetid);
        resetpassword = findViewById(R.id.resetpassword);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value= (int) snapshot.getChildrenCount();
//                Toast.makeText(getContext(), "value = "+value, Toast.LENGTH_SHORT).show();
                GenericTypeIndicator<List<User>> genericTypeIndicator =new GenericTypeIndicator<List<User>>(){};
//                Toast.makeText(getContext(), "!!!", Toast.LENGTH_SHORT).show();
                userList=snapshot.getValue(genericTypeIndicator);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForgotPassword.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

        recoverpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = resetid.getText().toString().trim();
                String password = resetpassword.getText().toString().trim();
                if(mail.isEmpty())
                    Toast.makeText(ForgotPassword.this, "Enter your email id!", Toast.LENGTH_SHORT).show();
                else
                {
                    //send mail to recover password
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ForgotPassword.this, "Password recovery mail sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(ForgotPassword.this, "User account doesnt exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, LogIn.class);
                startActivity(intent);
            }
        });
    }
}