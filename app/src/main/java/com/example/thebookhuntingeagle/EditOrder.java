package com.example.thebookhuntingeagle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thebookhuntingeagle.database.OrderDataSource;
import com.example.thebookhuntingeagle.model.Order;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.model.enums.OrderStatus;
import com.example.thebookhuntingeagle.model.enums.ShipOption;
import com.example.thebookhuntingeagle.util.LoggedUser;


public class EditOrder extends AppCompatActivity {

    private ImageView imgEditOrderAvatar;
    private TextView txtEditOrderUserName;
    private TextView txtEditOrderBookTileValue;
    private TextView txtEditOrderBookPriceValue;
    private TextView txtEditOrderStatus;
    private RadioButton rdShippingEditOrderDelivery;
    private RadioButton rdShippingEditOrderPickup;
    private Button btnEditOrderUpdateShip;
    private Button btnEditOrderConcludeOrder;
    private Button btnEditOrderCancelOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        //References for controls
        imgEditOrderAvatar = findViewById(R.id.imgEditOrderAvatar);
        txtEditOrderUserName = findViewById(R.id.txtEditOrderUserName);
        txtEditOrderBookTileValue = findViewById(R.id.txtEditOrderBookTileValue);
        txtEditOrderBookPriceValue = findViewById(R.id.txtEditOrderBookPriceValue);
        txtEditOrderStatus = findViewById(R.id.txtEditOrderStatus);
        rdShippingEditOrderDelivery = findViewById(R.id.rdShippingEditOrderDelivery);
        rdShippingEditOrderPickup = findViewById(R.id.rdShippingEditOrderPickup);
        btnEditOrderUpdateShip = findViewById(R.id.btnEditOrderUpdateShip);
        btnEditOrderConcludeOrder = findViewById(R.id.btnEditOrderConcludeOrder);
        btnEditOrderCancelOrder = findViewById(R.id.btnEditOrderCancelOrder);

        //Updates header
        User user = LoggedUser.getUser();
        loadUserData(user);
        //Fill up order data
        Order order = (Order) getIntent().getSerializableExtra("order");
        loadOrderData(order);
        //
        OrderDataSource ods = new OrderDataSource(EditOrder.this);

        btnEditOrderUpdateShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ods.open();
                boolean updated;
                if (rdShippingEditOrderDelivery.isChecked())
                    updated = ods.updateShip(order.getId(), ShipOption.DELIVERY);
                else
                    updated = ods.updateShip(order.getId(), ShipOption.PICKUP);
                ods.close();
                String msg;
                if (updated) {
                    msg = "Order updated successfully!";
                    setResult(RESULT_OK);
                    finish();
                } else {
                    msg = "Error on updating order!";
                }
                Toast.makeText(EditOrder.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btnEditOrderConcludeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                ods.open();
                if (ods.conclude(order.getId())) {
                    msg = "Order updated successfully!";
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    msg = "Error on updating order!";
                }
                ods.close();
                Toast.makeText(EditOrder.this, msg, Toast.LENGTH_LONG).show();

            }
        });

        btnEditOrderCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                ods.open();
                if (ods.cancel(order.getId())) {
                    msg = "Order updated successfully!";
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    msg = "Error on updating order!";
                }
                ods.close();
                Toast.makeText(EditOrder.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserData(User user) {
        //Header update
        String[] fullName = user.getName().split(" ");
        txtEditOrderUserName.setText(fullName[0]);
        imgEditOrderAvatar.setImageResource(user.getAvatar());
    }

    private void loadOrderData(Order order) {
        txtEditOrderBookTileValue.setText(order.getSale().getBookTitle());
        txtEditOrderBookPriceValue.setText(String.format("CAD %.2f", order.getSale().getDiscountedPrice()));
        if (order.getShip().equals(ShipOption.DELIVERY))
            rdShippingEditOrderDelivery.setChecked(true);
        else
            rdShippingEditOrderPickup.setChecked(true);
        txtEditOrderStatus.setText(order.getStatus().name());

        //Only orders under stand by (CREATED) can be edited
        if (order.getStatus().equals(OrderStatus.CREATED)) {
            rdShippingEditOrderDelivery.setEnabled(true);
            rdShippingEditOrderPickup.setEnabled(true);
            btnEditOrderUpdateShip.setEnabled(true);
            btnEditOrderConcludeOrder.setEnabled(true);
            btnEditOrderCancelOrder.setEnabled(true);
        }

        //Only seller can conclude order
        if (!LoggedUser.getUser().getId().equals(order.getSale().getSeller().getId())) {
            btnEditOrderConcludeOrder.setEnabled(false);
        }

    }
}