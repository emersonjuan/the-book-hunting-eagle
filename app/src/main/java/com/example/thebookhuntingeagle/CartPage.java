package com.example.thebookhuntingeagle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebookhuntingeagle.model.Sale;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

public class CartPage extends AppCompatActivity {

    private TextView txtOrderDetailBookTitle;
    private TextView txtOrderDetailBookAuthor;
    private TextView txtOrderDetailBookEdition;
    private TextView txtOrderDetailBookCondition;
    private TextView txtOrderDetailBookPrice;
    private TextView txtOrderDetailBookDiscount;
    private TextView txtOrderDetailBookFinalPrice;
    private TextView txtOrderDetailBookUserName;
    private TextView txtOrderDetailBookUserEmail;
    private TextView txtOrderDetailBookUserPhone;
    private TextView txtOrderDetailBookUserCity;
    private TextView txtOrderDetailBookUserAddress;
    private ImageView imgAvatar;
    private TextView txtUserName;
    private Button btnPlaceOrder;

    ActivityResultLauncher<Intent> shippingOptionsForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        //References for controls
        identifyObjectReferences();
        //Sale coming from previous screen
        Sale sale = (Sale) getIntent().getSerializableExtra("sale");
        //Load sale data to current screen
        loadSale(sale);
        loadUserData(LoggedUser.getUser());

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shippingIntent = new Intent(CartPage.this, ShippingOptions.class);
                shippingIntent.putExtra("sale", sale);
                shippingOptionsForResult.launch(shippingIntent);
            }
        });

    }

    private void identifyObjectReferences() {
        txtOrderDetailBookTitle = findViewById(R.id.txtOrderDetailBookTitle);
        txtOrderDetailBookAuthor = findViewById(R.id.txtOrderDetailBookAuthor);
        txtOrderDetailBookEdition = findViewById(R.id.txtOrderDetailBookEdition);
        txtOrderDetailBookCondition = findViewById(R.id.txtOrderDetailBookCondition);
        txtOrderDetailBookPrice = findViewById(R.id.txtOrderDetailBookPrice);
        txtOrderDetailBookDiscount = findViewById(R.id.txtOrderDetailBookDiscount);
        txtOrderDetailBookFinalPrice = findViewById(R.id.txtOrderDetailBookFinalPrice);
        txtOrderDetailBookUserName = findViewById(R.id.txtOrderDetailBookUserName);
        txtOrderDetailBookUserEmail = findViewById(R.id.txtOrderDetailBookUserEmail);
        txtOrderDetailBookUserPhone = findViewById(R.id.txtOrderDetailBookUserPhone);
        txtOrderDetailBookUserCity = findViewById(R.id.txtOrderDetailBookUserCity);
        txtOrderDetailBookUserAddress = findViewById(R.id.txtOrderDetailBookUserAddress);
        imgAvatar = findViewById(R.id.imgCurrentUserBookDetails);
        txtUserName = findViewById(R.id.txtUserNameBookDetails);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
    }

    private void loadSale(Sale sale) {
        txtOrderDetailBookTitle.setText(sale.getBookTitle());
        txtOrderDetailBookAuthor.setText(sale.getAuthor());
        txtOrderDetailBookEdition.setText(sale.getEdition());
        txtOrderDetailBookCondition.setText(sale.getCondition().name());
        txtOrderDetailBookPrice.setText(String.format("CAD %.2f", sale.getPrice()));
        txtOrderDetailBookDiscount.setText(String.format("%.2f %%", sale.getDiscount()));
        txtOrderDetailBookFinalPrice.setText(String.format("CAD %.2f", sale.getDiscountedPrice()));
        txtOrderDetailBookUserName.setText(sale.getSeller().getName());
        txtOrderDetailBookUserEmail.setText(sale.getSeller().getEmail());
        txtOrderDetailBookUserPhone.setText(sale.getSeller().getPhone());
        txtOrderDetailBookUserCity.setText(sale.getSeller().getCity().getName());
        txtOrderDetailBookUserAddress.setText(sale.getSeller().getAddress());
    }

    private void loadUserData(User user) {
        imgAvatar.setImageResource(user.getAvatar());
        txtUserName.setText(user.getName().split(" ")[0]);
    }
}