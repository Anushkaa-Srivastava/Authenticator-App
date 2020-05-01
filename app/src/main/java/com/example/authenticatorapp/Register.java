package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText FullName, Email, Pass, Phone;
    Button Register2;
    TextView tvLogin;
    FirebaseAuth fAuth;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FullName=findViewById(R.id.etname);
        Email=findViewById(R.id.etemail);
        Pass=findViewById(R.id.etpass);
        Phone=findViewById(R.id.etphone);
        Register2=findViewById(R.id.btnregister);
        tvLogin=findViewById(R.id.tv);
        fAuth=FirebaseAuth.getInstance();
        pb=findViewById(R.id.pbar);
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        Register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString().trim();
                String password=Pass.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Pass.setError("Password is required");
                    return;
                }
                if(password.length()<6)
                {
                    Pass.setError("Password must be more than 6 characters long");
                    return;
                }
                pb.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this,"User created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
