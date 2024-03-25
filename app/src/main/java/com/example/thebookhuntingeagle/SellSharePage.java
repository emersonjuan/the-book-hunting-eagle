package com.example.thebookhuntingeagle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellSharePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_share_page);
        /* In this page the user will be able to include books to sell or share. This information will
        later be shown on the bookshelf page.
        There should be edit text views to insert the user books' data and the information inserted there should
        be stored in the database.
        Not sure yet how to manage this reviews feature.
        By clicking the add to bookshelf button the information will be stored in the database and the user
        will be redirected to the bookshelf page, so he can see the new data there.
         */
        Button btnAddShelf = (Button) findViewById(R.id.btnAddShelf);

        btnAddShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellSharePage.this, BookshelfPage.class));
            }
        });
    }
}