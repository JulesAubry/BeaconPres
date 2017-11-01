package com.estimote.proximitycontent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.estimote.proximitycontent.R;

public class ShowProduct extends AppCompatActivity {
    private String idBeacon = "";
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        Bundle b = getIntent().getExtras();
        if(b != null)
            idBeacon = b.getString("key");

        switch (idBeacon) {
            case "80d3fef04d1bc31366d9ae295de22730":
                break;
            case "a2132dfaee5d947574ba39a2d6e4d107":
                break;
            case "f8893b99d382feb066100b40034e0d2e":
                break;
            default:
                break;
        }
    }

}
