package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        /* In this page the user will be able to change his information inside the database.
        The hint texts inside the input cells should be the last information stored about the user.
        Can we do this?
        Once the user clicks the edit button then this input cells would be available for editing.
        Once he is done with the editing and clicks the save button it should update the database and
        send the user to the UserHomePage.
         */
        Button btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageAccount.this, UserHomePage.class));
            }
        });
    }
}