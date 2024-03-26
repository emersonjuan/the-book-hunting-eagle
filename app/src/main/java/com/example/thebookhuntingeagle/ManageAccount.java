package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.CityDataSource;
import com.example.thebookhuntingeagle.database.UserDataSource;
import com.example.thebookhuntingeagle.model.City;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.AvatarImageList;
import com.example.thebookhuntingeagle.util.LoggedUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageAccount extends AppCompatActivity {

    Button btnReset;
    Button btnSave;
    ImageView imgUserManage;
    FloatingActionButton btnPreviousAvatar;
    FloatingActionButton btnNextAvatar;
    TextView txtEditEmail;
    EditText inputEditName;
    EditText inputEditAddress;
    Spinner citySpinnerEdit;
    EditText inputEditPhone;
    EditText inputEditPassword;
    AvatarImageList avatarList = new AvatarImageList();

    City selectedCity;
    List<City> cityList;

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

        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);
        imgUserManage = findViewById(R.id.imgUserEdit);
        btnPreviousAvatar = findViewById(R.id.btnPreviousAvatarManage);
        btnNextAvatar = findViewById(R.id.btnNextAvatarManage);
        txtEditEmail = findViewById(R.id.txtEditEmail);
        inputEditName = findViewById(R.id.inputEditName);
        inputEditAddress = findViewById(R.id.inputEditAddress);
        citySpinnerEdit = findViewById(R.id.citySpinnerEdit);
        inputEditPhone = findViewById(R.id.inputEditPhone);
        inputEditPassword = findViewById(R.id.inputEditPassword);
        avatarList = new AvatarImageList();

        //Loads list of cities from data base to spinner
        CityDataSource cds = new CityDataSource(this);
        cds.open();
        cityList = cds.findAll();
        cds.close();

        //Adapter for list of cities on the spinner
        ArrayAdapter<City> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinnerEdit.setAdapter(adapter);

        //Loads user data on controllers
        loadCurrentUserData();

        citySpinnerEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCurrentUserData();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Input validations
                List<String> invalidInputs = validateInputFields();
                if (invalidInputs.size()>0) {
                    Toast.makeText(ManageAccount.this,
                            "Please, fill up input " + invalidInputs.get(0) + " properly.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                //Data persistence
                UserDataSource uds = new UserDataSource(ManageAccount.this);
                uds.open();
                String msg;
                User editedUser = mountNewUserData();
                if (uds.update(editedUser)) {
                    msg = "User data updated successfully.";
                    uds.close();
                    LoggedUser.setUser(editedUser);
                    setResult(RESULT_OK, new Intent());
                    finish();
                } else {
                    msg = "Error on user data update.";
                }
                uds.close();
                Toast.makeText(ManageAccount.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnPreviousAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgUserManage.setImageResource(avatarList.previousAvatar());
            }
        });

        btnNextAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgUserManage.setImageResource(avatarList.nextAvatar());
            }
        });

    }

    /**
     * Copies information from current logged user to screen input controls
     */
    private void loadCurrentUserData() {

        User user = LoggedUser.getUser();
        imgUserManage.setImageResource(user.getAvatar());
        avatarList.setCurrentAvatar(user.getAvatar());
        String[] email = user.getEmail().split("@");
        txtEditEmail.setText(email[0]);
//        txtEditEmail.setText(email[0] + "\n@" + email[1]);
        inputEditName.setText(user.getName());
        inputEditAddress.setText(user.getAddress());
        selectedCity = user.getCity();
        citySpinnerEdit.setSelection(cityList.indexOf(selectedCity));
        inputEditPhone.setText(user.getPhone());
        inputEditPassword.setText(user.getPassword());
    }

    /**
     * Merges data from Logged user with data from screen controls
     * @return User editedUser
     */
    private User mountNewUserData() {
        User editedUser = LoggedUser.getUser();
        editedUser.setAvatar(avatarList.getCurrentAvatar());
        editedUser.setName(inputEditName.getText().toString().trim());
        editedUser.setAddress(inputEditAddress.getText().toString().trim());
        editedUser.setCity(selectedCity);
        editedUser.setPhone(inputEditPhone.getText().toString().trim());
        editedUser.setPassword(inputEditPassword.getText().toString().trim());

        return editedUser;
    }

    /**
     * Check validation of screen control inputs
     */
    private List<String> validateInputFields() {

        List<String> invalidFields = new ArrayList<>();

        //Name Validation
        if (inputEditName.getText().toString().trim().isEmpty())
            invalidFields.add("Name");
        //Address Validation
        if (inputEditAddress.getText().toString().trim().isEmpty())
            invalidFields.add("Address");
        //Phone validation
        if (inputEditPhone.getText().toString().trim().length()!=10)
            invalidFields.add("Phone");
        //Password validation
        if (inputEditPassword.getText().toString().trim().isEmpty())
            invalidFields.add("Password");

        return invalidFields;
    }
}