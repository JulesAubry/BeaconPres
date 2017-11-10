package com.estimote.proximitycontent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

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
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_cartlist);

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
        inflater.inflate(R.menu.layout_return_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.returnMenu:
                super.finish();
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
    }
}
