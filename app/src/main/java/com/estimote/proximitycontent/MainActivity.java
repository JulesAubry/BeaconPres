package com.estimote.proximitycontent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.repackaged.gson_v2_3_1.com.google.gson.reflect.TypeToken;
import com.estimote.proximitycontent.estimote.EstimoteCloudBeaconDetails;
import com.estimote.proximitycontent.estimote.EstimoteCloudBeaconDetailsFactory;
import com.estimote.proximitycontent.estimote.ProximityContentManager;
import com.google.gson.Gson;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.*;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

   private static final String TAG = "MainActivity";
   private ProximityContentManager proximityContentManager;
   private Map<String, String> beaconsIDNames;
   public static DatabaseHandler db;
   public static List<CartItem> cart;
   private static Context context;
   private Menu menu;
   private android.support.v7.widget.Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar myToolbar = ( android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        MainActivity.context = getApplicationContext();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("cartList", null);
        if(json == null) {
            cart = new ArrayList<CartItem>();
        }
        else {
            Type type = new TypeToken<ArrayList<CartItem>>() {
            }.getType();
            cart = gson.fromJson(json, type);
        }

        setUPDB();

        beaconsIDNames = new HashMap<String, String>();
        beaconsIDNames.put( "80d3fef04d1bc31366d9ae295de22730", "pink_15");
        beaconsIDNames.put( "a2132dfaee5d947574ba39a2d6e4d107", "Lemonade");
        beaconsIDNames.put( "f8893b99d382feb066100b40034e0d2e", "pink_3");


        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        "80d3fef04d1bc31366d9ae295de22730",
                        "a2132dfaee5d947574ba39a2d6e4d107",
                        "f8893b99d382feb066100b40034e0d2e"),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    setActivity(beaconDetails);
                } else {
                    //TO DO
                }
            }
        });
    }

    public void setActivity(EstimoteCloudBeaconDetails beaconDetails) {
        String idDevice = proximityContentManager.getNearestBeaconManager().getCurrentlyNearestDeviceID();
        if(beaconDetails.getBeaconName().equals(beaconsIDNames.get(idDevice))) {
            Intent intent = new Intent(this, ShowProduct.class);
            Bundle b = new Bundle();
            b.putString("key", idDevice);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        invalidateOptionsMenu();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }

    public void setUPDB() {
        db = new DatabaseHandler(this);

        db.onUpgrade(db.getReadableDatabase(),1,2);

        db.addCategory(new Category("Shoes"));
        db.addCategory(new Category("Pants"));
        db.addCategory(new Category("Socks"));
        /*
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Category> products = db.getAllCategorys();

        for (Category cn : products) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
        */

        Category pdr = db.getCategory("Shoes");
        db.addProduct(new Product("TIMBERLAND NEW MAN", 78.95, R.drawable.shoes_1, pdr.getId()));
        db.addProduct(new Product("NIKE AIR MAX", 48.55, R.drawable.shoes_2, pdr.getId()));
        db.addProduct(new Product("CONVERSE ALL STARS", 65.85, R.drawable.shoes_3, pdr.getId()));
        db.addProduct(new Product("WINTER WOMAN SHOES", 39.05, R.drawable.shoes_4, pdr.getId()));
        db.addProduct(new Product("MAN SHOES HIGH QUALITY", 125.55, R.drawable.shoes_5, pdr.getId()));


        Category pdr_2 = db.getCategory("Socks");
        db.addProduct(new Product("YELLOW SOCKS", 10.45, R.drawable.socks_1, pdr_2.getId()));
        db.addProduct(new Product("RED NIKE SOCKS", 11.85, R.drawable.socks_2, pdr_2.getId()));
        db.addProduct(new Product("WOMAN FANTASY SOCKS", 25.95, R.drawable.socks_3, pdr_2.getId()));

        Category pdr_3 = db.getCategory("Pants");
        db.addProduct(new Product("BLUE JEAN", 119.99, R.drawable.pants_1, pdr_3.getId()));
        db.addProduct(new Product("RUNNING PANTS", 25.85,R.drawable.pants_2, pdr_3.getId()));
        db.addProduct(new Product("MAN JOGGING", 85.85, R.drawable.pants_3, pdr_3.getId()));
        db.addProduct(new Product("WOMAN PANTS HIGH QUALITY", 149.99,R.drawable.pants_4, pdr_3.getId()));
        db.addProduct(new Product("WOMAN SHORT BLACK",  25.99, R.drawable.pants_5, pdr_3.getId()));


        /*// Reading all contacts
        Log.d("Reading: ", "Reading all shoes..");
        List<Product> shoes = db.getAllProducts("Shoes");

        for (Product cn : shoes) {
            String log = "Id: " + cn.getIdS() + " ,Name: " + cn.getNameS();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }*/
    }

    public byte[] toByteArray(int i) {
        Drawable d = this.getDrawable(i);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
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

        this.menu = menu;

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

    public static Context getAppContext() {
        return MainActivity.context;
    }

    public static void saveCartList() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(cart);

        editor.putString("cartList", json);
        editor.commit();
    }
}
