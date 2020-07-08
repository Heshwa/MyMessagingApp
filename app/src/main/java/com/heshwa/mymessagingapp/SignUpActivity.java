package com.heshwa.mymessagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSign;
    private EditText edtUserName ,edtEmail ,edtPassword;
    private TextView txtAlreadyHAveAccount;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSign = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);
        txtAlreadyHAveAccount = findViewById(R.id.txtAlreadyHaveAccount);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String userName ,email , password;
                userName =edtUserName.getText().toString();
                email = edtEmail.getText().toString();
                password =edtPassword.getText().toString();

                if(userName.equals("") || email.equals("") || password.equals(""))
                {
                    Toast.makeText(SignUpActivity.this,"All field are Mandatory",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                userRef.child(mAuth.getCurrentUser().getUid()).child("Name").setValue(userName)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    Toast.makeText(SignUpActivity.this,userName + " Successfully register",Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(SignUpActivity.this,"error: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                            }
                            else {
                                Toast.makeText(SignUpActivity.this,"error: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }
}