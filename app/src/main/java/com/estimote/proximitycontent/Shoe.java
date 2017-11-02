package com.estimote.proximitycontent;

/**
 * Created by Jules on 02/11/2017.
 */

public class Shoe extends Product {

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }



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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
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
    private byte[] image;
    private int productID;


    public Shoe(int id, String name, int idS, String nameS, double price, byte[] image, int productID) {
        super(id, name);
        this.idS = id;
        this.nameS = nameS;
        this.price = price;
        this.image = image;
        this.productID = productID;
    }

    public Shoe() {
        super();
    }

}
