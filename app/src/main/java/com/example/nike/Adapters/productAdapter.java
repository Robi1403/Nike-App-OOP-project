package com.example.nike.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.R;
import com.example.nike.models.productModel;

import java.text.DecimalFormat;
import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    private Context context;
    private List<productModel> productModels;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(productModel product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public productAdapter(Context context, List<productModel> productModels) {
        this.context = context;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //populates the frontend with the layout "product_list" design
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull productAdapter.ViewHolder holder, int position) {
        String formatedPrice = formatWithCommas(productModels.get(position).getProductPrice());


        Glide.with(context).load(productModels.get(position).getProductImage()).into(holder.Image);
        holder.pname.setText(productModels.get(position).getProductName());
        holder.pprice.setText("₱ "+ formatedPrice);




        // listens for click then gets the inforation of the proct that has been clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemClick(productModels.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView pname,pprice,pstocks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // links to frontend
            Image = itemView.findViewById(R.id.productImage);
            pname = itemView.findViewById(R.id.productName);
            pprice = itemView.findViewById(R.id.productPrice);


        }
    }
    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }
}
