package com.example.thebookhuntingeagle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.thebookhuntingeagle.model.Sale;

import java.util.List;

public class BookshelfAdapter extends ArrayAdapter<Sale> {


    public BookshelfAdapter(Context context, List<Sale> sales) {
        super(context, R.layout.bookshelf_item, sales);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup partent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customView = inflater.inflate(R.layout.bookshelf_item, partent, false);

        Sale sale = getItem(position);
        //Book title
        TextView txtBookName = customView.findViewById(R.id.txtBookShelfItemName);
        txtBookName.setText(sale.getBookTitle());
        //Book price
        TextView txtBookAuthor = customView.findViewById(R.id.txtBookShelfItemAuthor);
        txtBookAuthor.setText(sale.getAuthor());
        //Book city
        TextView txtBookCondition = customView.findViewById(R.id.txtBookShelfItemCond);
        txtBookCondition.setText(sale.getCondition().toString());

        return customView;
    }
}
