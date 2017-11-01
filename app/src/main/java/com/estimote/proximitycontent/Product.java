package com.estimote.proximitycontent;

/**
 * Created by Jules on 01/11/2017.
 */

import android.media.Image;

import java.util.*;

public class Product {

    protected String name;
    protected int numbers;
    protected Map<String, Map<Double,Image>> products;

    public Product() {
        this.name = "Not Found";
        this.numbers = 0;
        this.products = new HashMap<String,Map<Double,Image>>();
    }

    public Product(String name) {
        this.name = name;
        this.products =  new HashMap<String,Map<Double, Image>>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public Map<String, Map<Double, Image>> getProducts() {
        return products;
    }

    public void addProduct(String n, Double p, Image i) {
        this.numbers ++;
        Map map = new HashMap<Double,Image>(1);
        map.put(p,i);
        this.products.put(n,map);
    }

    public void removeProduct(String n) {
        if(this.products.containsKey(n)) {
            this.products.remove(n);
            this.numbers--;
        }
    }
}
