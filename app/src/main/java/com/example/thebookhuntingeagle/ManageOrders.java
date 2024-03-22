package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);
        /* In this page the user will see all his recent orders information, on a listview, with
        data retrieved from the database (and that were saved in it during the activities performed
        on the cart page). Clicking on the options of this listview the user will be able to edit or
        cancel an order. Here I don't know if it's better to use radio buttons or listview. Maybe radio buttons.
        And then once the user checks the radio button and next clicks on the edit or cancel button he
        will be able to perform the chosen action.
        The user can also check the radio button and then change the shipping option of that specific order.
        He will only be able to change the shipping option if it is different from the one stored in
        the database and if the book status is different than delivered.
        If these conditions are filed then once the user clicks the pay additional fee, if there is
        an additional fee the app will show a pop-up message saying the fee was payed.
        By clicking on the save button all the new information should be stored in the database and
        the user redirected to the UserHomePage.
         */
        Button btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageOrders.this, UserHomePage.class));
            }
        });
    }
}