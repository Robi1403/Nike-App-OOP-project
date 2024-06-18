package com.example.nike.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nike.R;
import com.example.nike.models.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends AppCompatActivity {

    //for buttons
    Button onBackBtn4, onLogOutBtn;

    FirebaseUser user;
    DatabaseReference reference;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        TextView username, Email, PhoneNumber;

        username = findViewById(R.id.UserName);
        Email = findViewById(R.id.email);
        PhoneNumber = findViewById(R.id.phoneNumber);

        // fetch the users personal data and sets it in the frontend
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileModel userprofile = snapshot.getValue(ProfileModel.class);

                if (userprofile != null) {
                    String fname = userprofile.getFname();
                    String lname = userprofile.getLname();
                    String email = userprofile.getEmail();
                    String phoneNumber = userprofile.getPhoneNumber();

                    username.setText(fname + " " + lname);
                    Email.setText(email);
                    PhoneNumber.setText(phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilePage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });



        //linking buttons to button ids
        onLogOutBtn = findViewById(R.id.logoutBtn);
        onBackBtn4 = findViewById(R.id.backBtn4);

        //button functions
        onBackBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfilePage.this, HomePage.class);
                startActivity(intent);
                finish();;

            }
        });

        onLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfilePage.this, LoginPage.class); //change Home.class
                startActivity(intent);
                finish();;

            }
        });
    }
}