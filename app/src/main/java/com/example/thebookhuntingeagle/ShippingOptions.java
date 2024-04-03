package com.example.thebookhuntingeagle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

public class ShippingOptions extends AppCompatActivity {
    /* This page should show the buyers ordered items, sum up its prices after discount and add the
    shipping fees when applicable (considering the buyer also has the option to go to the seller
    and pick the book up. As all other user pages this one should also show the avatar and the
    name of the current user.
     */
    TextView txtNameCurrentUser;
    ImageView imgCurrentUser;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                        loadUserData();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_options);
        txtNameCurrentUser = findViewById(R.id.txtNameCurrentUser);
        imgCurrentUser = findViewById(R.id.imgCurrentUser);
    }
    /**
     * Updates Name and Avatar on screen
     */
    private void loadUserData() {
        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtNameCurrentUser.setText(fullName[0]);
        imgCurrentUser.setImageResource(user.getAvatar());
    }
}