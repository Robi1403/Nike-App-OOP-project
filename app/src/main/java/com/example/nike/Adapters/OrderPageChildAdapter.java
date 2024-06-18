package com.example.nike.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.models.OrderPageChild;
import com.example.nike.R;

import java.util.List;
import java.text.DecimalFormat;

public class OrderPageChildAdapter extends RecyclerView.Adapter<OrderPageChildAdapter.ViewHolder> {
    List<OrderPageChild> childList;
    Context context;

    public OrderPageChildAdapter(List<OrderPageChild> childList, Context context) {
        this.childList = childList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderPageChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //populates the frontend with the layout "orders" design
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPageChildAdapter.ViewHolder holder, int position) {
        // displays the informations in the frontend
        OrderPageChild childItem = childList.get(position);

        String formatedPrice = formatWithCommas(childItem.getProductPrice());

        Glide.with(context).load(childItem.getProductImage()).into(holder.pimage);
        holder.pname.setText(childItem.getProductName());
        holder.pprice.setText("₱ "+ formatedPrice);
        holder.psize.setText("US " + childItem.getProductSize());



    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pname,pprice,psize;
        ImageView pimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // links data to frontend
            pname = itemView.findViewById(R.id.productName);
            pprice = itemView.findViewById(R.id.productPrices);
            pimage = itemView.findViewById(R.id.productImage);
            psize = itemView.findViewById(R.id.productSize);

        }
    }
    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }
}
