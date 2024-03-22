package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.DatabaseHelper;

public class RegisterPage extends AppCompatActivity {

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        dbh = new DatabaseHelper(this);
        TextView tvName = findViewById(R.id.inputNewName);
        TextView tvAddress = findViewById(R.id.inputNewAddress);
        TextView tvPhone = findViewById(R.id.inputNewPhone);
        TextView tvEmail = findViewById(R.id.inputNewEmail);
        TextView tvPassword = findViewById(R.id.inputNewPassword);

        Button btnNewRegister = findViewById(R.id.btnNewRegister);

        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString();
                String address = tvAddress.getText().toString();
                String phone = tvPhone.getText().toString();
                String email = tvEmail.getText().toString();
                String password = tvPassword.getText().toString();

                boolean isAdded = dbh.addUser(email, password, name, address, phone, "New Westminster");
                String returnMsg = "";
                if (isAdded)
                   returnMsg = "User registered successfully.";
                else
                   returnMsg = "Error on registering user.";

                Toast.makeText(RegisterPage.this, returnMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}