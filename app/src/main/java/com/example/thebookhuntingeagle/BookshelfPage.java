package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thebookhuntingeagle.database.SaleDataSource;
import com.example.thebookhuntingeagle.model.Sale;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.LoggedUser;

import java.util.ArrayList;
import java.util.List;

public class BookshelfPage extends AppCompatActivity {

    TextView txtNameUserAccount;
    ImageView imageViewAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf_page);

        /* In this page the user will see all his recent bookshelf information, on a list, with
        data retrieved from the database (and that were saved in it during the activities performed
        on the sell share page). Here maybe will need to add radio buttons next to each list option
        and once this radio button is clicked the user will be redirected to the Sell Share Page, where
        the input cells will be available for editing. So in this case I don't know if it is necessary
        to keep the edit shelf button nor the other options that are shown after this button including the
        save button once the user will be redirected to the sell share page and the new info will be
        stored from that page once the user clicks the add to the bookshelf button there... This button save
        now could just be renamed home and send the user back to the user home page.
        Another option would be to keep the design as it is but find a way to show the bookshelf data
        in a certain way that the cells could be edited.
         */
        txtNameUserAccount = findViewById(R.id.txtNameUserAccount8);
        imageViewAvatar = findViewById(R.id.imgUser7);
        ListView listview = findViewById(R.id.listViewBookshelf);

        loadUserData();

        List<Sale> sales = new ArrayList<>();

        BookshelfAdapter adapter = new BookshelfAdapter(this, sales);
        listview.setAdapter(adapter);

        //Datasource for sales
        SaleDataSource sds = new SaleDataSource(this);
        sds.open();
        adapter.clear();

        List<Sale> salesTest = sds.findAvailableBooksByUserId(LoggedUser.getUser().getId());


        adapter.addAll(salesTest);
        adapter.notifyDataSetInvalidated();
        sds.close();
    }
    private void loadUserData() {
        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtNameUserAccount.setText(fullName[0]);
        imageViewAvatar.setImageResource(user.getAvatar());
    }
}