package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        /* insert code to catch users information and store it in the database.
        Then the button register should store the information in the database and
        lead the user to the UserHomePage (if possible).
         */
        Button btnNewRegister = (Button) findViewById(R.id.btnNewRegister);

        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this, UserHomePage.class));
            }
        });
    }
}