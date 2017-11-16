package com.estimote.proximitycontent;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by samita on 11/8/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private List<CartItem> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView, priceTextView;
        public Spinner sizeSpinner;
        public EditText numberEditText;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);
            sizeSpinner = (Spinner) view.findViewById(R.id.sizeSpinner);
            numberEditText = (EditText) view.findViewById(R.id.numberEditText);

            numberEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                public void afterTextChanged(Editable s) {
                    String str = s.toString();
                    if(!str.equals("")) {
                        if (str.equals("0")) {
                            cartList.remove(getAdapterPosition());
                            numberEditText.post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });
                            MainActivity.saveCartList();
                        } else {
                            cartList.get(getAdapterPosition()).setQuantity(Integer.parseInt(str));
                            DecimalFormat numberFormat = new DecimalFormat("0.00");
                            MainActivity.saveCartList();
                        }
                    }
                }

            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_view, parent, false);
        return new CartAdapter.MyViewHolder(itemView);
    }

    public CartAdapter(List<CartItem> cartList) {
        this.cartList = cartList;
    }


    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
       final CartItem cart = cartList.get(position);

        holder.imageView.setImageResource( cart.getProduct().getImage());

        holder.nameTextView.setText(cart.getProduct().getNameS());

        DecimalFormat numberFormat = new DecimalFormat("0.00");

        holder.priceTextView.setText(numberFormat.format(cart.getProduct().getPrice())+ " €");
        holder.numberEditText.setText(Integer.toString(cart.getQuantity()));


        holder.sizeSpinner.setSelection(cart.getSize());

        holder.sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {
                cartList.get(position).setSize(pos);
                MainActivity.saveCartList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
