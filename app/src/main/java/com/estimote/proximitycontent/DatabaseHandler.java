package com.estimote.proximitycontent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jules on 02/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shoppingKotkantie";

    // Product table name
    private static final String TABLE_PRODUCT = "Product";

    // Product Table Columns names
    private static final String PRODUCT_ID = "productID";
    private static final String PRODUCT_NAME = "productNAME";

    // Shoe table name
    private static final String TABLE_SHOE = "Shoe";

    // Product Table Columns names
    private static final String SHOE_ID = "shoeID";
    private static final String SHOE_NAME = "shoeNAME";
    private static final String SHOE_PRICE = "shoePRICE";
    private static final String SHOE_IMAGE = "shoeIMAGE";
    private static final String SHOE_PRODUCT_ID = "shoePRODUCTID";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + PRODUCT_ID + " INTEGER PRIMARY KEY," + PRODUCT_NAME + " TEXT UNIQUE)";
        db.execSQL(CREATE_PRODUCT_TABLE);
        String CREATE_SHOE_TABLE = "CREATE TABLE " + TABLE_SHOE + "("
                + SHOE_ID + " INTEGER PRIMARY KEY," + SHOE_NAME + " TEXT UNIQUE,"
                + SHOE_PRICE + " DOUBLE," +  SHOE_IMAGE + " BLOB," + SHOE_PRODUCT_ID + " TEXT,"
                + " FOREIGN KEY ("+SHOE_PRODUCT_ID+") REFERENCES "+TABLE_PRODUCT+" ("+PRODUCT_ID+"))";
        db.execSQL(CREATE_SHOE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);

        // Create tables again
        onCreate(db);
    }

    // Adding new shoe
    void addShoe(Shoe shoe) {
        SQLiteDatabase db = this.getWritableDatabase();

        Product pdr = getProduct("Shoes");
        ContentValues values = new ContentValues();
        values.put(SHOE_NAME, shoe.getNameS());
        values.put(SHOE_PRICE, shoe.getPrice());
        values.put(SHOE_IMAGE, shoe.getImage());
        values.put(SHOE_PRODUCT_ID, pdr.getId());

        // Inserting Row
        db.insert(TABLE_SHOE, null, values);
        db.close(); // Closing database connection
    }

    Shoe getShoe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHOE, new String[] { SHOE_ID,
                        SHOE_NAME, SHOE_PRICE, SHOE_IMAGE, SHOE_PRODUCT_ID}, SHOE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Shoe shoe = new Shoe(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getDouble(2), cursor.getBlob(3),Integer.parseInt(cursor.getString(4)) );
        return shoe;
    }

    public List<Shoe> getAllShoes() {
        List<Shoe> shoeList = new ArrayList<Shoe>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHOE;

        Product pdr = getProduct("Shoes");

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Shoe shoe = new Shoe();
                shoe.setIdS( Integer.parseInt(cursor.getString(0)));
                shoe.setNameS(cursor.getString(1));
                shoe.setPrice(cursor.getDouble(2));
                shoe.setImage(cursor.getBlob(3));
                shoe.setProductID(Integer.parseInt(cursor.getString(4)));
                shoeList.add(shoe);
            } while (cursor.moveToNext());
        }

        return shoeList;
    }

    public int updateShoe(Shoe shoe) {
        SQLiteDatabase db = this.getWritableDatabase();
        Product pdr = getProduct("Shoes");

        ContentValues values = new ContentValues();
        values.put(SHOE_NAME, shoe.getNameS());
        values.put(SHOE_PRICE, shoe.getPrice());
        values.put(SHOE_IMAGE, shoe.getImage());
        values.put(SHOE_PRODUCT_ID, pdr.getId());

        // updating row
        return db.update(TABLE_PRODUCT, values, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(shoe.getIdS()) });
    }

    public void deleteShoe(Shoe shoe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOE, SHOE_ID + " = ?",
                new String[] { String.valueOf(shoe.getIdS()) });
        db.close();
    }


    public int getShoesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SHOE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Adding new product
    void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());

        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
    }

    Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { PRODUCT_ID,
                        PRODUCT_NAME}, PRODUCT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        return product;
    }

    Product getProduct(String str) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { PRODUCT_ID,
                        PRODUCT_NAME}, PRODUCT_NAME + "=?",
                new String[] { String.valueOf(str) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product contact = new Product();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                productList.add(contact);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());

        // updating row
        return db.update(TABLE_PRODUCT, values, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
        db.close();
    }


    public int getProductsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
