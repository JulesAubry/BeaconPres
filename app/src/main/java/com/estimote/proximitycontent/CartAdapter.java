package com.estimote.proximitycontent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

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

        Bitmap bm = BitmapFactory.decodeByteArray(cart.getProduct().getImage(), 0, cart.getProduct().getImage().length);
        DisplayMetrics dm = new DisplayMetrics();

        holder.imageView.setMinimumHeight(dm.heightPixels);
        holder.imageView.setMinimumWidth(dm.widthPixels);
        holder.imageView.setImageBitmap(bm);

        holder.nameTextView.setText(cart.getProduct().getNameS());

        DecimalFormat numberFormat = new DecimalFormat("0.00");

        holder.priceTextView.setText(numberFormat.format(cart.getProduct().getPrice() * cart.getQuantity()) + " €");
        holder.numberEditText.setText(Integer.toString(cart.getQuantity()));
        holder.sizeSpinner.setSelection(cart.getSize());

        holder.numberEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if(!str.equals("")) {
                    if(str.equals("0")) {
                        cartList.remove(position);
                        notifyDataSetChanged();
                    }
                    else {
                        cartList.get(position).setQuantity(Integer.parseInt(str));
                        DecimalFormat numberFormat = new DecimalFormat("0.00");
                        holder.priceTextView.setText(numberFormat.format(cart.getProduct().getPrice() * cart.getQuantity()) + " €");
                    }
                }

            }

            public void afterTextChanged(Editable s) {}

        });

        holder.sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int pos, long id) {
                cartList.get(position).setSize(pos);
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
