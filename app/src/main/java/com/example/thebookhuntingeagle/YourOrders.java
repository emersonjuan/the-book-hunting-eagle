package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

public class YourOrders extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private TextView txtNameUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_orders);
        /* This class should show the user's orders history. Once he clicks one specific
           order it leads him to the manage orders page where he will be able to edit that order.
        */
        txtNameUserAccount = findViewById(R.id.txtNameUserOrders);
        imageViewAvatar = findViewById(R.id.imgUserOrders);

        loadUserData();
    }
    private void loadUserData() {
        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtNameUserAccount.setText(fullName[0]);
        imageViewAvatar.setImageResource(user.getAvatar());
    }
}
