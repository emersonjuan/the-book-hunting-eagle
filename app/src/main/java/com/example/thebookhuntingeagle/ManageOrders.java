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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thebookhuntingeagle.database.OrderDataSource;
import com.example.thebookhuntingeagle.model.Order;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

import java.util.ArrayList;
import java.util.List;

public class ManageOrders extends AppCompatActivity {

    private ImageView imgManageOrdersAvatar;
    private TextView txtManageOrdersUserName;
    OrderlistAdapter adapter;

    ActivityResultLauncher<Intent> editOrderForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        loadOrderList();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_orders);

        //References for controls
        ListView listview = findViewById(R.id.listViewManageOrders);
        imgManageOrdersAvatar = findViewById(R.id.imgManageOrdersAvatar);
        txtManageOrdersUserName = findViewById(R.id.txtManageOrdersUserName);

        //Updates header
        loadUserData();

        //List of sale items
        List<Order> orders = new ArrayList<>();
        adapter = new OrderlistAdapter(this, orders);
        listview.setAdapter(adapter);

        //Populates list
        loadOrderList();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order orderItem = (Order) parent.getItemAtPosition(position);
                Intent editOrderIntent = new Intent(ManageOrders.this, EditOrder.class);
                editOrderIntent.putExtra("order", orderItem);
                editOrderForResult.launch(editOrderIntent);
            }
        });
    }

    private void loadUserData() {
        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtManageOrdersUserName.setText(fullName[0]);
        imgManageOrdersAvatar.setImageResource(user.getAvatar());
    }

    private void loadOrderList() {
        OrderDataSource ods = new OrderDataSource(this);
        ods.open();
        adapter.clear();
        adapter.addAll(ods.findByBuyerOrSeller(LoggedUser.getUser()));
        adapter.notifyDataSetInvalidated();
        ods.close();
    }
}