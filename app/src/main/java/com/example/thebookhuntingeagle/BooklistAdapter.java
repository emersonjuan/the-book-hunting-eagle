package com.example.thebookhuntingeagle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.thebookhuntingeagle.model.Sale;

import java.util.List;

public class BooklistAdapter extends ArrayAdapter<Sale> {


    public BooklistAdapter(Context context, List<Sale> sales) {
        super(context, R.layout.booklist_item, sales);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup partent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customView = inflater.inflate(R.layout.booklist_item, partent, false);

        Sale sale = getItem(position);
        //Book title
        TextView txtBookName = customView.findViewById(R.id.txtSaleItemName);
        txtBookName.setText(sale.getBookTitle());
        //Book price
        TextView txtBookPrice = customView.findViewById(R.id.txtSaleItemPrice);
        txtBookPrice.setText(String.format("CAD %.2f", sale.getDiscountedPrice()));
        //Book city
        TextView txtBookCity = customView.findViewById(R.id.txtSaleitemCity);
        txtBookCity.setText(sale.getSeller().getCity().getName());

        return customView;
    }
}
