package com.estimote.proximitycontent;

/**
 * Created by Jules on 07/11/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView, priceTextView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        }
    }


    public ProductAdapter(List<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productsList.get(position);

        Bitmap bm = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        DisplayMetrics dm = new DisplayMetrics();

        holder.imageView.setMinimumHeight(dm.heightPixels);
        holder.imageView.setMinimumWidth(dm.widthPixels);
        holder.imageView.setImageBitmap(bm);

        holder.nameTextView.setText(product.getNameS());
        holder.priceTextView.setText(product.getPrice() + " €");
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}