package com.estimote.proximitycontent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.*;

import java.util.List;

/**
 * Created by samita on 11/7/2017.
 */

public class ShowCart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter mAdapter;
    private int count;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cartlist);
        count = MainActivity.cart.size();

        android.support.v7.widget.Toolbar myToolbar = ( android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar_cart);
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateTotal();
            }
        });

        mAdapter = new CartAdapter(MainActivity.cart);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_return, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.returnMenu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void calculateTotal() {
        double total = 0.0;
        for(int i = 0; i < MainActivity.cart.size(); i++) {
            total += MainActivity.cart.get(i).getProduct().getPrice() * MainActivity.cart.get(i).getQuantity() ;
        }

        TextView eT = (TextView)findViewById(R.id.totalTextView);
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        eT.setText("Total = " + numberFormat.format(total) + " €");

        MainActivity.saveCartList();
    }

    public void sendEmail(View view) {
        if(MainActivity.cart.size() > 0 ) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"k7auju00@students.oamk.fi"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Your Order");
            i.putExtra(Intent.EXTRA_TEXT, toStringEmail());
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
                MainActivity.cart.clear();
                MainActivity.saveCartList();
                Toast.makeText(getApplicationContext(), "Thanks for your order", Toast.LENGTH_SHORT).show();
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String toStringEmail() {
        String str = "";
        double total = 0.0;
        for(int i = 0; i < MainActivity.cart.size(); i++) {
            Product pdr = MainActivity.cart.get(i).getProduct();
            Category ctr = MainActivity.db.getCategory(pdr.getCategoryID());

            str += "Product (" + ctr.getName() + ") = ";
            str += "reference : " + pdr.getNameS() + ", ";
            str += "size : " + getSize(MainActivity.cart.get(i).getSize()) + ", ";
            str += "quantity : " + MainActivity.cart.get(i).getQuantity() + ", ";
            str += "price : " + pdr.getPrice()*MainActivity.cart.get(i).getQuantity() + "€.";

            total += pdr.getPrice()*MainActivity.cart.get(i).getQuantity();

            str += "\n\n";
        }

        DecimalFormat numberFormat = new DecimalFormat("0.00");
        str += "Total = " + numberFormat.format(total) + " €";

        return str;
    }

    public String getSize(int size) {
        String str = "";

        switch (size) {
            case 0 :
                str = "XXS";
                break;
            case 1 :
                str = "XS";
                break;
            case 2 :
                str = "S";
                break;
            case 3 :
                str = "M";
                break;
            case 4 :
                str = "L";
                break;
            case 5 :
                str = "XL";
                break;
            case 6 :
                str = "XXL";
                break;
            case 7 :
                str = "XXXL";
                break;
            default :
                str = "XXS";
        }

        return str;
    }
}
