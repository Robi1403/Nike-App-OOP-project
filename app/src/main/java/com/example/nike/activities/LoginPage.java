package com.example.nike.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    Button onSignupBtn, onLoginBtn;
    TextInputLayout Email, Password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);

        onSignupBtn = findViewById(R.id.SignupBtn);
        onLoginBtn = findViewById(R.id.LoginBtn);

        Email = findViewById(R.id.UserEmail);
        Password =  findViewById(R.id.UserPassword);

        //when login button is clicked this will run
        onLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
                progressBar.setVisibility(View.VISIBLE);


            }
        });

        //when signup button is clicked this will run
        onSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private Boolean validateEmail(){
        String value = Email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        if (value.isEmpty()){
            Email.setError("Field cannot be empty");
            return  false;
        }else if (!value.matches(emailPattern)) {
            Email.setError("Invalid Email");
            return false;
        }else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String value = Password.getEditText().getText().toString();
        if (value.isEmpty()) {
            Password.setError("Field cannot be empty");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser() {
        progressBar.setVisibility(View.GONE);
        validatePassword();
        validateEmail();

        if (!validateEmail() || !validatePassword()){
            return;
        }

        validateUser();


    }

    private void validateUser() {
        //validates user in database
        String UserInputEmail = Email.getEditText().getText().toString();
        String UserInputPassword = Password.getEditText().getText().toString();

        firebaseAuth.signInWithEmailAndPassword(UserInputEmail,UserInputPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            startActivity(intent);
                            finish();
                        }else{
                            //error message when user inputs wrong informations
                            Password.setError("Email or Password is Incorrect");
                            Email.setError("Email or Password is Incorrect");
                            Toast.makeText(LoginPage.this, "Error", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }

}