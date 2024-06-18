package com.example.nike.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.nike.R;

public class MainActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN = 5000; // the delay to the next screen from splash screen
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN ); // removes the status bar (yong sa taas a mga things)
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() { // part of the code starts the next activity -> Log in
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginPage.class); //
                startActivity(intent); // starts the next activity (leads to log in screen)
                finish(); // prevents looping of the splash screen window
            }
        }, SPLASH_SCREEN); // used the delay
    }
}