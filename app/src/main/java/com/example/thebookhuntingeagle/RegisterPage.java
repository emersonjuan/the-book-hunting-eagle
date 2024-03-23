package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.CityDataSource;
import com.example.thebookhuntingeagle.database.UserDataSource;
import com.example.thebookhuntingeagle.model.City;
import com.example.thebookhuntingeagle.model.User;

import java.util.List;

public class RegisterPage extends AppCompatActivity {


    List<City> cityList;
    int selectedCityId;
    City selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        /* insert code to catch users information and store it in the database.
        Then the button register should store the information in the database and
        lead the user to the UserHomePage (if possible).
         */

        //References for view objects
        EditText inputNewName = findViewById(R.id.inputNewName);
        EditText inputNewAddress = findViewById(R.id.inputNewAddress);
        Spinner citySpinner = findViewById(R.id.citySpinner);
        EditText inputNewPhone = findViewById(R.id.inputNewPhone);
        EditText inputNewEmail = findViewById(R.id.inputNewEmail);
        EditText inputNewPassword = findViewById(R.id.inputNewPassword);
        Button btnNewRegister = findViewById(R.id.btnNewRegister);

        //Table City handler
        CityDataSource cds = new CityDataSource(this);
        cds.open();
        cityList = cds.findAll();
        cds.close();

        //Adapter for list of cities on the spinner
        ArrayAdapter<City> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        //Listener for Item Selection City Spinner
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        //Listener on Register Button clicked
        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Input validations
                if (!isInputDataValidated())
                    Toast.makeText(RegisterPage.this, "Please, fill up inputs properly.",
                            Toast.LENGTH_LONG).show();

                UserDataSource uds = new UserDataSource(RegisterPage.this);
                uds.open();

                //Protects repeated emails (username)
                if (uds.findByEmail(inputNewEmail.getText().toString()).isPresent()) {
                    Toast.makeText(RegisterPage.this, "E-mail already registered.",
                            Toast.LENGTH_LONG).show();
                    uds.close();
                    return;
                }

                //New user registration
                User newUser = new User(
                        null,
                        inputNewName.getText().toString(),
                        inputNewAddress.getText().toString(),
                        selectedCity,
                        inputNewPhone.getText().toString(),
                        inputNewEmail.getText().toString(),
                        inputNewPassword.getText().toString()
                );
                boolean inserted = uds.insert(newUser);
                uds.close();

                //Output message
                String msg;
                if (inserted) {
                    msg = "User registered successfully!";
                    finish();
                } else {
                    msg = "Error on inserting new user. Please, check the inputs.";
                }
                Toast.makeText(RegisterPage.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isInputDataValidated() {
        return true;
    }
}