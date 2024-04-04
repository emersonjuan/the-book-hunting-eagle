package com.example.thebookhuntingeagle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thebookhuntingeagle.model.Order;
import com.example.thebookhuntingeagle.util.LoggedUser;

import java.util.List;

public class OrderlistAdapter extends ArrayAdapter<Order> {


    public OrderlistAdapter(Context context, List<Order> orders) {
        super(context, R.layout.orderlist_item, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup partent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customView = inflater.inflate(R.layout.orderlist_item, partent, false);

        Order order = getItem(position);
        //Book title
        TextView txtBookName = customView.findViewById(R.id.txtOrderItemBookName);
        txtBookName.setText(order.getSale().getBookTitle());
        //Order Date
        TextView txtOrderDate = customView.findViewById(R.id.txtOrderItemDate);
        txtOrderDate.setText(order.getStartDate().toString());
        //Buyer or Seller user
        TextView txtOrderItemBuyerSeller = customView.findViewById(R.id.txtOrderItemBuyerSeller);
        String userType;
        if (order.getBuyer().getId().equals(LoggedUser.getUser().getId()))
            userType = "Buy";
        else
            userType = "Sell";
        txtOrderItemBuyerSeller.setText(userType);
        //Order Status
        TextView txtOrderStatus = customView.findViewById(R.id.txtOrderItemStatus);
        txtOrderStatus.setText(order.getStatus().name());

        return customView;
    }
}
