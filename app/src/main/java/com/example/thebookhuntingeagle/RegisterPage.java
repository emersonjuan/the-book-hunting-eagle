package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.CityDataSource;
import com.example.thebookhuntingeagle.database.UserDataSource;
import com.example.thebookhuntingeagle.model.City;
import com.example.thebookhuntingeagle.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {


    List<City> cityList;
    int selectedCityId;
    int currentAvatarId;
    City selectedCity;

    EditText inputNewName;
    EditText inputNewAddress;
    Spinner citySpinner;
    EditText inputNewPhone;
    EditText inputNewEmail;
    EditText inputNewPassword;
    //Buttons
    Button btnNewRegister;
    FloatingActionButton btnPreviousAvatar;
    FloatingActionButton btnNextAvatar;
    //ImageView
    ImageView imageViewAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        /* insert code to catch users information and store it in the database.
        Then the button register should store the information in the database and
        lead the user to the UserHomePage (if possible).
         */

        //References for view objects
        //TextInputs
        inputNewName = findViewById(R.id.inputNewName);
        inputNewAddress = findViewById(R.id.inputNewAddress);
        citySpinner = findViewById(R.id.citySpinner);
        inputNewPhone = findViewById(R.id.inputNewPhone);
        inputNewEmail = findViewById(R.id.inputNewEmail);
        inputNewPassword = findViewById(R.id.inputNewPassword);
        //Buttons
        btnNewRegister = findViewById(R.id.btnNewRegister);
        btnPreviousAvatar = findViewById(R.id.btnPreviousAvatar);
        btnNextAvatar = findViewById(R.id.btnNextAvatar);
        //ImageView
        imageViewAvatar = findViewById(R.id.imageViewAvatar);

        //List of avatar images
        List<Integer> avatarList = new ArrayList<>(Arrays.asList(
                R.drawable.avatar0,
                R.drawable.avatar1,
                R.drawable.avatar2
        ));
        imageViewAvatar.setImageResource(avatarList.get(0));

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
                List<String> invalidInputs = validateInputFields();
                if (invalidInputs.size()>0) {
                    Toast.makeText(RegisterPage.this,
                            "Please, fill up input " + invalidInputs.get(0) + " properly.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

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
                        avatarList.get(currentAvatarId),
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

        btnPreviousAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAvatarId = (--currentAvatarId+avatarList.size())%avatarList.size();
                imageViewAvatar.setImageResource(avatarList.get(currentAvatarId));
            }
        });

        btnNextAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAvatarId = ++currentAvatarId%avatarList.size();
                imageViewAvatar.setImageResource(avatarList.get(currentAvatarId));
            }
        });
    }

    private List<String> validateInputFields() {

        List<String> invalidFields = new ArrayList<>();

        //Name Validation
        if (inputNewName.getText().toString().trim().isEmpty())
            invalidFields.add("Name");
        //Address Validation
        if (inputNewAddress.getText().toString().trim().isEmpty())
            invalidFields.add("Address");
        //Phone validation
        if (inputNewPhone.getText().toString().trim().length()!=10)
            invalidFields.add("Phone");
        //Email validation
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = pattern.matcher(inputNewEmail.getText().toString().trim());
        if (!matcher.matches())
            invalidFields.add("E-mail");
        //Password validation
        if (inputNewPassword.getText().toString().trim().isEmpty())
            invalidFields.add("Password");

        return invalidFields;
    }
}