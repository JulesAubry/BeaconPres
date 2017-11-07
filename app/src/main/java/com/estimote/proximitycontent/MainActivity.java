package com.estimote.proximitycontent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.proximitycontent.estimote.EstimoteCloudBeaconDetails;
import com.estimote.proximitycontent.estimote.EstimoteCloudBeaconDetailsFactory;
import com.estimote.proximitycontent.estimote.ProximityContentManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

   private static final String TAG = "MainActivity";
   private ProximityContentManager proximityContentManager;
   private Map<String, String> beaconsIDNames;
   public static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        db.addProduct(new Product("TIMBERLAND NEW MAN", 78.9, toByteArray(R.drawable.shoes_1), pdr.getId()));
        db.addProduct(new Product("NIKE AIR MAX", 48.50, toByteArray(R.drawable.shoes_2), pdr.getId()));

        Category pdr_2 = db.getCategory("Socks");
        db.addProduct(new Product("YELLOW SOCKS", 10.4, toByteArray(R.drawable.socks_1), pdr_2.getId()));
        db.addProduct(new Product("RED NIKE SOCKS", 11.6, toByteArray(R.drawable.socks_2), pdr_2.getId()));

        Category pdr_3 = db.getCategory("Pants");
        db.addProduct(new Product("BLUE JEAN", 120.0, toByteArray(R.drawable.pants_1), pdr_3.getId()));
        db.addProduct(new Product("RUNNING PANTS", 25.85, toByteArray(R.drawable.pants_2), pdr_3.getId()));

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


}
