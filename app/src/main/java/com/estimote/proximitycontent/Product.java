package com.estimote.proximitycontent;

/**
 * Created by Jules on 01/11/2017.
 */

import android.media.Image;

import java.util.*;

public class Product {

    protected int id;
    protected String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product() {

    }

    public Product(String name) {
        this.name = name;
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
