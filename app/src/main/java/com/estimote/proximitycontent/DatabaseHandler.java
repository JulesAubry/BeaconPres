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

    // Category table name
    private static final String TABLE_CATEGORY = "Category";

    // Category Table Columns names
    private static final String CATEGORY_ID = "categoryID";
    private static final String CATEGORY_NAME = "categoryNAME";

    // Product table name
    private static final String TABLE_PRODUCT = "Product";

    // Category Table Columns names
    private static final String PRODUCT_ID = "productID";
    private static final String PRODUCT_NAME = "productNAME";
    private static final String PRODUCT_PRICE = "productPRICE";
    private static final String PRODUCT_IMAGE = "productIMAGE";
    private static final String PRODUCT_CATEGORY_ID = "productCATEGORYID";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + CATEGORY_ID + " INTEGER PRIMARY KEY," + CATEGORY_NAME + " TEXT UNIQUE)";
        db.execSQL(CREATE_CATEGORY_TABLE);
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + PRODUCT_ID + " INTEGER PRIMARY KEY," + PRODUCT_NAME + " TEXT UNIQUE,"
                + PRODUCT_PRICE + " DOUBLE," +  PRODUCT_IMAGE + " BLOB," + PRODUCT_CATEGORY_ID + " TEXT,"
                + " FOREIGN KEY ("+PRODUCT_CATEGORY_ID+") REFERENCES "+TABLE_CATEGORY+" ("+CATEGORY_ID+"))";
        db.execSQL(CREATE_PRODUCT_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        // Create tables again
        onCreate(db);
    }

    public void restartDB(SQLiteDatabase db) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
    }

    // Adding new product
    void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getNameS());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_IMAGE, product.getImage());
        values.put(PRODUCT_CATEGORY_ID, product.getCategoryID());

        // Inserting Row
        db.insert(TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
    }

    Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { PRODUCT_ID,
                        PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE, PRODUCT_CATEGORY_ID}, PRODUCT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getDouble(2), Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)) );
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
                Product product = new Product();
                product.setIdS( Integer.parseInt(cursor.getString(0)));
                product.setNameS(cursor.getString(1));
                product.setPrice(cursor.getDouble(2));
                product.setImage(Integer.parseInt(cursor.getString(3)));
                product.setCategoryID(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public List<Product> getAllProducts(String str) {
        Category pdr = getCategory(str);

        List<Product> productList = new ArrayList<Product>();
        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCT, new String[] { PRODUCT_ID,
                        PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE, PRODUCT_CATEGORY_ID}, PRODUCT_CATEGORY_ID + "=?",
                new String[] { String.valueOf(pdr.getId()) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdS( Integer.parseInt(cursor.getString(0)));
                product.setNameS(cursor.getString(1));
                product.setPrice(cursor.getDouble(2));
                product.setImage(Integer.parseInt(cursor.getString(3)));
                product.setCategoryID(Integer.parseInt(cursor.getString(4)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getNameS());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_IMAGE, product.getImage());
        values.put(PRODUCT_CATEGORY_ID, product.getCategoryID());

        // updating row
        return db.update(TABLE_CATEGORY, values, CATEGORY_ID + " = ?",
                new String[] { String.valueOf(product.getIdS()) });
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getIdS()) });
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

    // Adding new category
    void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());

        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection
    }

    Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { CATEGORY_ID,
                        CATEGORY_NAME}, CATEGORY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        return category;
    }

    Category getCategory(String str) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { CATEGORY_ID,
                        CATEGORY_NAME}, CATEGORY_NAME + "=?",
                new String[] { String.valueOf(str) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category contact = new Category();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                categoryList.add(contact);
            } while (cursor.moveToNext());
        }

        return categoryList;
    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, category.getName());

        // updating row
        return db.update(TABLE_CATEGORY, values, CATEGORY_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, CATEGORY_ID + " = ?",
                new String[] { String.valueOf(category.getId()) });
        db.close();
    }


    public int getCategoriesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
