package com.estimote.proximitycontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.proximitycontent.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import java.util.ArrayList;
import java.util.List;

import static com.estimote.proximitycontent.MainActivity.cart;

public class ShowProduct extends AppCompatActivity  implements ProductAdapter.OnItemClicked{
    private String idBeacon = "";
    private List<Product> productList;
    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        android.support.v7.widget.Toolbar myToolbar = ( android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar_product);
        setSupportActionBar(myToolbar);

        Bundle b = getIntent().getExtras();
        if(b != null)
            idBeacon = b.getString("key");

        switch (idBeacon) {
            case "80d3fef04d1bc31366d9ae295de22730": //pink_15
                productList = MainActivity.db.getAllProducts("Shoes");
                setTitle("Shoes");
                break;
            case "a2132dfaee5d947574ba39a2d6e4d107": //Lemonade
                productList = MainActivity.db.getAllProducts("Socks");
                setTitle("Socks");
                break;
            case "f8893b99d382feb066100b40034e0d2e": //pink_3
                productList = MainActivity.db.getAllProducts("Pants");
                setTitle("Pants");
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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter.setOnClick(this);

    }

    public void setTitle(String str) {
        getSupportActionBar().setTitle(str);
    }

    @Override
    public void onItemClick(int position) {
        Product product = productList.get(position);
        cart.add(new CartItem(product,1,0));
        invalidateOptionsMenu();
        MainActivity.saveCartList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_cart, menu);

        if (cart.size() > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.showcart),  FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.DARK_GREY, cart.size());
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.showcart));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.showcart:
                ActionItemBadge.update(item, cart.size());
                Intent intent = new Intent(this, ShowCart.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (cart.size() > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.showcart),  FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.DARK_GREY, cart.size());
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.showcart));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }
}
