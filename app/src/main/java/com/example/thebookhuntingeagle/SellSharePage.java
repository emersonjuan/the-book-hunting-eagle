package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.thebookhuntingeagle.model.Sales;
import com.example.thebookhuntingeagle.model.User;

public class SellSharePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_share_page);
        /* In this page the user will be able to include books to sell or share. This information will
        later be shown on the bookshelf page.
        There should be edit text views to insert the user books' data and the information inserted there should
        be stored in the database.
        Not sure yet how to manage this reviews feature.
        By clicking the add to bookshelf button the information will be stored in the database and the user
        will be redirected to the bookshelf page, so he can see the new data there.
         */

        EditText bookTitle = findViewById(R.id.inputBookTitle);
        EditText bookAuthor = findViewById(R.id.inputBookAuthor);
        EditText bookEdition = findViewById(R.id.inputBookEdition);
        EditText bookPrice = findViewById(R.id.inputPriceSell);
        EditText bookDiscount = findViewById(R.id.inputBookDiscount);
        final RadioButton bookCondNew = (RadioButton)findViewById(R.id.rdBookCondNew);
        final RadioButton bookCondGood = (RadioButton)findViewById(R.id.rdBookCondGood);
        final RadioButton bookCondPoor = (RadioButton)findViewById(R.id.rdBookCondPoor);
        final RadioButton bookSellOption = (RadioButton)findViewById(R.id.rdSell);
        final RadioButton bookShareOption = (RadioButton)findViewById(R.id.rdShare);
        Button btnAddShelf = (Button) findViewById(R.id.btnAddShelf);

        btnAddShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                //New user registration
//                User newUser = new User(
//                        null,
//                        avatarList.getCurrentAvatar(),
//                        inputNewName.getText().toString(),
//                        inputNewAddress.getText().toString(),
//                        selectedCity,
//                        inputNewPhone.getText().toString(),
//                        inputNewEmail.getText().toString(),
//                        inputNewPassword.getText().toString()
//                );
//                boolean inserted = uds.insert(newUser);
//                uds.close();

                //Output message
                String msg;
                if (inserted) {
                    msg = "Book registered successfully!";
                    finish();
                } else {
                    msg = "Error on inserting new user. Please, check the inputs.";
                }
                Toast.makeText(SellSharePage.this, msg, Toast.LENGTH_LONG).show();
                startActivity(new Intent(SellSharePage.this, BookshelfPage.class));
            }
        });
    }
}