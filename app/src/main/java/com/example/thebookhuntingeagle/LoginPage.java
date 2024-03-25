package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.UserDataSource;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

import java.util.Optional;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        /* insert condition to check the veracity of the username and the password.
        Then the button enter should lead the user to the UserHomePage if info is correct.
        Otherwise the app should throw a pop-up error message.
         */
        Button btnEnter = (Button) findViewById(R.id.btnEnter);
        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);

        LoggedUser.setUser(null);

        UserDataSource ds = new UserDataSource(LoginPage.this);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Search user on database
                ds.open();
                Optional<User> userOp = ds.findByEmail(inputEmail.getText().toString().trim());
                ds.close();

                //Credentials validation
                String msg;
                if (userOp.isPresent()) {
                    User user = userOp.get();
                    if (user.getPassword().equals(inputPassword.getText().toString())) {
                        LoggedUser.setUser(user);
                        Intent newIntent = new Intent(LoginPage.this, UserHomePage.class);
                        startActivity(newIntent);
                        finish();
                        msg = "Welcome " + user.getName();
                    } else {
                        msg = "Invalid user password";
                    }
                } else {
                    msg = "Username not registered";
                }
                Toast.makeText(LoginPage.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}