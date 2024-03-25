package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LogoutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_page);
        TimerTask task = new TimerTask( ) {
            @Override
            public void run(){
                finish();
                startActivity(new Intent(LogoutPage.this, MainActivity.class));
            }
        };
        Timer opening = new Timer();
        opening.schedule(task,3000);
    }
}