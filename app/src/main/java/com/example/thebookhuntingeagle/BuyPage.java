package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.thebookhuntingeagle.database.SaleDataSource;
import com.example.thebookhuntingeagle.model.Sale;

import java.util.ArrayList;
import java.util.List;

public class BuyPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);

        //References for controls
        SearchView searchView = findViewById(R.id.search);
        ListView listview = findViewById(R.id.listviewBooks);

        //List of sale items
        List<Sale> sales = new ArrayList<>();
        BooklistAdapter adapter = new BooklistAdapter(this, sales);
        listview.setAdapter(adapter);

        //Datasource for sales
        SaleDataSource sds = new SaleDataSource(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sds.open();
                String searchTxt = searchView.getQuery().toString();
                adapter.clear();
                adapter.addAll(sds.findByTitle(searchTxt));
                adapter.notifyDataSetInvalidated();
                sds.close();

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sale saleItem = (Sale) parent.getItemAtPosition(position);
                Intent orderDetailIntent = new Intent(BuyPage.this, CartPage.class);
                orderDetailIntent.putExtra("sale", saleItem);
                startActivity(orderDetailIntent);
            }
        });
    }
}