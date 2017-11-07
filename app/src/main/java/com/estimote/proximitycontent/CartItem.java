package com.estimote.proximitycontent;

import com.estimote.proximitycontent.Product;

/**
 * Created by Jules on 07/11/2017.
 */

public class CartItem {
    private Product product;
    private int quantity;
    private int size;

    public CartItem(Product product) {
        this.product = product;
    }

    public CartItem(Product product, int quantity, int size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
