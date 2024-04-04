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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebookhuntingeagle.database.SaleDataSource;
import com.example.thebookhuntingeagle.database.UserDataSource;
import com.example.thebookhuntingeagle.model.Sale;
import com.example.thebookhuntingeagle.model.User;
import com.example.thebookhuntingeagle.util.BookCondition;
import com.example.thebookhuntingeagle.util.LoggedUser;
import com.example.thebookhuntingeagle.util.ShareSaleOption;

public class SellSharePage extends AppCompatActivity {

    TextView txtNameUserAccount;
    ImageView imageViewAvatar;
    BookCondition bookCondition = null;
    ShareSaleOption bookOption = null;
    Double bookDesc;
    Double bkPrice;
    RadioButton bookCondNew;
    RadioButton bookCondGood;
    RadioButton bookCondPoor;
    RadioButton bookSellOption;
    RadioButton bookShareOption;
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
        SaleDataSource sds = new SaleDataSource(this);

        EditText bookTitle = findViewById(R.id.inputBookTitle);
        EditText bookAuthor = findViewById(R.id.inputBookAuthor);
        EditText bookEdition = findViewById(R.id.inputBookEdition);
        EditText bookPrice = findViewById(R.id.inputPriceSell);
        EditText bookDiscount = findViewById(R.id.inputBookDiscount);
        bookCondNew = (RadioButton)findViewById(R.id.rdBookCondNew);
        bookCondGood = (RadioButton)findViewById(R.id.rdBookCondGood);
        bookCondPoor = (RadioButton)findViewById(R.id.rdBookCondPoor);
        bookSellOption = (RadioButton)findViewById(R.id.rdSell);
        bookShareOption = (RadioButton)findViewById(R.id.rdShare);
        Button btnAddShelf = (Button) findViewById(R.id.btnAddShelf);
        txtNameUserAccount = findViewById(R.id.txtNameUserAccount4);
        imageViewAvatar = findViewById(R.id.imgUser4);
        RadioGroup rdGroupSellShare = findViewById(R.id.rdGroupSellShare);

        loadUserData();

        btnAddShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Open the database to insert book
                sds.open();

                //New book registration
                User user = LoggedUser.getUser();
                Sale newBook = new Sale(
                        null,
                        bookTitle.getText().toString(),
                        bookAuthor.getText().toString(),
                        bookEdition.getText().toString(),
                        bookCondition = getBookCondition(),
                        bookDesc = Double.parseDouble(bookDiscount.getText().toString()),
                        bkPrice = Double.parseDouble(bookPrice.getText().toString()),
                        bookOption = getShareSellOption(),
                        user
                );
                boolean inserted = sds.insert(newBook);
                sds.close();

                //Output message
                String msg;
                if (inserted) {
                    msg = "Book registered successfully!";
                    finish();
                } else {
                    msg = "Error on inserting new book. Please, check the inputs.";
                }
                Toast.makeText(SellSharePage.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        rdGroupSellShare.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdShare) {
                    bookPrice.setText("0.00");
                    bookPrice.setEnabled(false);
                    bookDiscount.setText("0.00");
                    bookDiscount.setEnabled(false);

                }
                else {
                    bookPrice.setEnabled(true);
                    bookDiscount.setEnabled(true);
                }
            }
        });

    }
    private BookCondition getBookCondition(){
        BookCondition value = null;
        if(bookCondNew.isChecked()){
            value = BookCondition.NEW;
        } else if (bookCondGood.isChecked()){
            value = BookCondition.GOOD;
        }else if(bookCondPoor.isChecked()) {
            value = BookCondition.POOR;
        }
        return value;
    }

    private ShareSaleOption getShareSellOption(){
        ShareSaleOption value = null;
        if(bookSellOption.isChecked()){
            value = ShareSaleOption.SELL;
        }else if(bookShareOption.isChecked()){
            value =  ShareSaleOption.SHARE;
        }
        return value;
    }
    private void loadUserData() {
        //Header update
        User user = LoggedUser.getUser();
        String[] fullName = user.getName().split(" ");
        txtNameUserAccount.setText(fullName[0]);
        imageViewAvatar.setImageResource(user.getAvatar());
    }
}