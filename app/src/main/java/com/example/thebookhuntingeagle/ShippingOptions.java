package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.OrderDataSource;
import com.example.thebookhuntingeagle.model.Order;
import com.example.thebookhuntingeagle.model.Sale;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.model.enums.OrderStatus;
import com.example.thebookhuntingeagle.model.enums.ShipOption;
import com.example.thebookhuntingeagle.util.LoggedUser;

import java.time.LocalDate;

public class ShippingOptions extends AppCompatActivity {

    private ImageView imgShippingUserAvatar;
    private TextView txtShippingUserName;
    private TextView txtShippedBookTitle;
    private TextView txtShippedBookSeller;
    private TextView txtShippingSubtotalValue;
    private TextView txtShippingTotalValue;
    private Button btnCreateOrder;
    private RadioGroup rdGroupShipping;

    private final double DELIVERY_FEE = 10.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_options);

        Sale sale = (Sale) getIntent().getSerializableExtra("sale");
        identifyObjectReferences();
        loadUserData(LoggedUser.getUser());
        loadSale(sale);
        loadFinalPrice(sale.getDiscountedPrice() + DELIVERY_FEE);

        rdGroupShipping.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                double finalPrice = sale.getDiscountedPrice();
                if (checkedId == R.id.rdShippingDelivery)
                    finalPrice += DELIVERY_FEE;
                loadFinalPrice(finalPrice);
            }
        });

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShipOption shipOption = ShipOption.DELIVERY;
                if (rdGroupShipping.getCheckedRadioButtonId() == R.id.rdShippingPickup)
                    shipOption = ShipOption.PICKUP;
                Order newOrder = new Order(
                        null,
                        sale,
                        LoggedUser.getUser(),
                        shipOption,
                        OrderStatus.CREATED,
                        LocalDate.now(),
                        null
                );
                OrderDataSource ods = new OrderDataSource(ShippingOptions.this);
                ods.open();
                boolean inserted = ods.insert(newOrder);
                ods.close();

                String msg;
                if (inserted) {
                    msg = "Order registered successfully!";
                    setResult(RESULT_OK);
                    finish();
                } else {
                    msg = "Error on inserting the new orders.";
                }
                Toast.makeText(ShippingOptions.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void identifyObjectReferences() {
        imgShippingUserAvatar = findViewById(R.id.imgShippingUserAvatar);
        txtShippingUserName = findViewById(R.id.txtShippingUserName);
        txtShippedBookTitle = findViewById(R.id.txtShippedBookTitle);
        txtShippedBookSeller = findViewById(R.id.txtShippedBookSeller);
        txtShippingSubtotalValue = findViewById(R.id.txtShippingSubtotalValue);
        txtShippingTotalValue = findViewById(R.id.txtShippingTotalValue);
        rdGroupShipping = findViewById(R.id.rdGroupShipping);
        btnCreateOrder = findViewById(R.id.btnCreateOrder);
    }

    private void loadUserData(User user) {
        //Header update
        imgShippingUserAvatar.setImageResource(user.getAvatar());
        txtShippingUserName.setText(user.getName().split(" ")[0]);
    }

    private void loadSale(Sale sale) {
        txtShippedBookTitle.setText(sale.getBookTitle());
        txtShippedBookSeller.setText(sale.getSeller().getName());
        txtShippingSubtotalValue.setText(String.format("CAD %.2f", sale.getDiscountedPrice()));
    }

    private void loadFinalPrice(double finalPrice) {
        txtShippingTotalValue.setText(String.format("CAD %.2f", finalPrice));
    }
}