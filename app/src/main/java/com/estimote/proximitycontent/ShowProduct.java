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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        Bundle b = getIntent().getExtras();
        if(b != null)
            idBeacon = b.getString("key");
    }

}
