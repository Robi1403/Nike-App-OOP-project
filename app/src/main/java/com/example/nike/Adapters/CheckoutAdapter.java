package com.example.nike.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.models.CartModel;
import com.example.nike.R;

import java.text.DecimalFormat;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    Context context;
    List<CartModel> checkoutModelList;
    int totalPrice = 0;

    public CheckoutAdapter(Context context, List<CartModel> checkoutModelList) {
        this.context = context;
        this.checkoutModelList = checkoutModelList;
    }

    @NonNull
    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckoutAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_list,parent,false));
    }

    //displaying data of items in checkout
    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.ViewHolder holder, int position) {
        String formatedPrice = formatWithCommas(checkoutModelList.get(position).getProductPrice());

        holder.cname.setText(checkoutModelList.get(position).getProductName());
        holder.cprice.setText("₱ "+ formatedPrice);
        holder.csize.setText(checkoutModelList.get(position).getProductSize());

        //pass total amount to Cart
        totalPrice = totalPrice + checkoutModelList.get(position).getProductPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return checkoutModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cname, csize, cprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cname = itemView.findViewById(R.id.checkout_name);
            cprice = itemView.findViewById(R.id.checkout_price);
            csize = itemView.findViewById(R.id.checkout_size);
        }
    }
    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }
}