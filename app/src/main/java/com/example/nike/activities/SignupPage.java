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
import com.example.nike.models.UserHelperCLass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {

    TextInputLayout FirstName, LastName, PhoneNumber, Email, Password;
    Button onLoginBtn, CreateAccountBtn;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN ); // removes the status bar (yong sa taas na mga things)
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // connects to the elements in the xml file or the UI
        onLoginBtn = findViewById(R.id.backToLoginBtn);
        CreateAccountBtn = findViewById(R.id.CreateAccount);
        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);

        FirstName = findViewById(R.id.NewFirstName);
        LastName =  findViewById(R.id.NewLastName);
        PhoneNumber = findViewById(R.id.NewPhoneNumber);
        Email = findViewById(R.id.NewEmail);
        Password = findViewById(R.id.NewPassword);



        CreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        // links to login page so when click user is directed to the log in page
        onLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    // checks if user have inputs and checks if the users input is the correct format fr the needed data
    private Boolean validateFname(){
        String value = FirstName.getEditText().getText().toString();
        if (value.isEmpty()){
            FirstName.setError("Field cannot be empty");
            return  false;
        }else {
            FirstName.setError(null);
            FirstName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateLname(){
        String value = LastName.getEditText().getText().toString();
        if (value.isEmpty()){
            LastName.setError("Field cannot be empty");
            return  false;
        }else {
            LastName.setError(null);
            LastName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber(){
        String value = PhoneNumber.getEditText().getText().toString();
        if (value.isEmpty()){
            PhoneNumber.setError("Field cannot be empty");
            return  false;
        } else if (value.length() >= 12) {
            PhoneNumber.setError("Phone cannot be greater than 11");
            return  false;
        } else {
            PhoneNumber.setError(null);
            PhoneNumber.setErrorEnabled(false);
            return true;
        }
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

    private Boolean validatePassword(){
        String value = Password.getEditText().getText().toString();
        if (value.isEmpty()){
            Password.setError("Field cannot be empty");
            return  false;
        }else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(){
        // gets the value from the UI
        String firstName = FirstName.getEditText().getText().toString();
        String lastName = LastName.getEditText().getText().toString();
        String phoneNumber = PhoneNumber.getEditText().getText().toString();
        String email = Email.getEditText().getText().toString();
        String password = Password.getEditText().getText().toString();


        validateFname();
        validateLname();
        validatePhoneNumber();
        validateEmail();
        validatePassword();

        if (!validateFname() || !validateLname() || !validatePhoneNumber() || !validateEmail() || !validatePassword()){

            return;
        }
        // Saves the data to the database
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){



                            UserHelperCLass savedatabase = new UserHelperCLass(firstName,lastName,email,password,phoneNumber);
                            String UserId = task.getResult().getUser().getUid();

                            firebaseDatabase.getReference().child("Users").child(UserId).setValue(savedatabase);
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(SignupPage.this, SuccessPage.class);
                            startActivity(intent);
                            finish();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignupPage.this, "Unable to create Account"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });






    }


}