package com.example.nike.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.models.CartModel;
import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<CartModel> cartModelList;
    int totalPrice = 0;

    //for database
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list,parent,false));
    }

    //displaying data of items in cart
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String formatedPrice = formatWithCommas(cartModelList.get(position).getProductPrice());

        Glide.with(context).load(cartModelList.get(position).getProductImage()).into(holder.pimage);
        holder.pname.setText(cartModelList.get(position).getProductName());
        holder.pprice.setText("₱"+ formatedPrice);
        holder.psize.setText(cartModelList.get(position).getProductSize());

        //for deleting item in the cart list
        holder.premove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("UserActivity").document(auth.getCurrentUser().getUid())
                        .collection("UserCart")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    cartModelList.remove(cartModelList.get(position));
                                    notifyDataSetChanged();

                                    // Displaying message that the item is removed
                                    Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //passing the total amount to Cart
        totalPrice = totalPrice + cartModelList.get(position).getProductPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pimage, premove;

        TextView pname, pprice, psize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.product_name);
            pprice = itemView.findViewById(R.id.product_price);
            psize = itemView.findViewById(R.id.product_size);
            pimage = itemView.findViewById(R.id.product_picture);
            premove = itemView.findViewById(R.id.remove);
        }
    }

    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }
}