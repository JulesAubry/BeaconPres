package com.estimote.proximitycontent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.estimote.proximitycontent.R;

import java.util.ArrayList;
import java.util.List;

public class ShowProduct extends AppCompatActivity {
    private String idBeacon = "";
    private List<Product> productList;
    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        Bundle b = getIntent().getExtras();
        if(b != null)
            idBeacon = b.getString("key");

        switch (idBeacon) {
            case "80d3fef04d1bc31366d9ae295de22730": //pink_15
                productList = MainActivity.db.getAllProducts("Shoes");
                break;
            case "a2132dfaee5d947574ba39a2d6e4d107": //Lemonade
                productList = MainActivity.db.getAllProducts("Socks");
                break;
            case "f8893b99d382feb066100b40034e0d2e": //pink_3
                productList = MainActivity.db.getAllProducts("Pants");
                break;
            default:
                productList = new ArrayList<Product>();
                break;
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerV);

        mAdapter = new ProductAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

}
