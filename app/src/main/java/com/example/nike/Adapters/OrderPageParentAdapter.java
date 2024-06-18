package com.example.nike.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nike.models.OrderPageChild;
import com.example.nike.models.OrderPageParent;
import com.example.nike.R;

import java.text.DecimalFormat;
import java.util.List;

public class OrderPageParentAdapter extends RecyclerView.Adapter<OrderPageParentAdapter.ViewHolder> {

    Context context;
    List<OrderPageParent> parentArrayList;
    List<OrderPageChild> childArrayList;

    public OrderPageParentAdapter(Context context, List<OrderPageParent> parentArrayList, List<OrderPageChild> childArrayList) {
        this.context = context;
        this.parentArrayList = parentArrayList;
        this.childArrayList = childArrayList;
    }

    @NonNull
    @Override
    public OrderPageParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //populates the frontend with the layout "order_list" design
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPageParentAdapter.ViewHolder holder, int position) {
        // displays the informations in the frontend
        OrderPageParent parentItem = parentArrayList.get(position);

        holder.Total.setText(parentItem.getTotalPrice());
        holder.OrderNum.setText("Order Number: " + parentItem.getOrderId());
        holder.NumItems.setText(String.valueOf(parentItem.getNumberOfItems()) + " item");

        OrderPageChildAdapter childItemAdapter = new OrderPageChildAdapter(childArrayList,context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.childView.setLayoutManager(linearLayoutManager);
        holder.childView.setAdapter(childItemAdapter);

    }

    @Override
    public int getItemCount() {
        return parentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView OrderNum, Total,NumItems;
        RecyclerView childView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNum = itemView.findViewById(R.id.orderNumber);
            Total = itemView.findViewById(R.id.productPrice);
            NumItems = itemView.findViewById(R.id.Items);
            childView = itemView.findViewById(R.id.productView);
        }
    }

    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }
}
