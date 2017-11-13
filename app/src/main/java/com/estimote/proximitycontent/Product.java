package com.estimote.proximitycontent;

/**
 * Created by Jules on 02/11/2017.
 */

public class Product {

    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCategoryID() {
        return productID;
    }

    public void setCategoryID(int productID) {
        this.productID = productID;
    }

    public int getIdS() {
        return idS;
    }

    public void setIdS(int idS) {
        this.idS = idS;
    }

    private int idS;
    private String nameS;
    private double price;
    private int image;
    private int productID;

    public Product(String nameS, double price, int image, int productID) {
        this.nameS = nameS;
        this.price = price;
        this.image = image;
        this.productID = productID;
    }

    public Product(int idS, String nameS, double price, int image, int productID) {
        this.idS = idS;
        this.nameS = nameS;
        this.price = price;
        this.image = image;
        this.productID = productID;
    }

    public Product() {
        super();
    }

}
