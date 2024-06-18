package com.example.nike.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Adapters.CartAdapter;
import com.example.nike.models.CartModel;
import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends AppCompatActivity {
    Button onBackBtn2, onProceedCheckoutBtn; //buttons declaration

    //for database
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount; //total amount declaration
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //linking buttons to button ids
        onProceedCheckoutBtn = findViewById(R.id.checkoutBtn);
        onBackBtn2 = findViewById(R.id.backBtn2);

        //for adding the data to database
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView_Cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);

        //for computing total amount
        overTotalAmount = findViewById(R.id.subTotal);

        //displaying the total amount
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        //for fetching the data from database
        db.collection("UserActivity").document(auth.getCurrentUser().getUid())
                .collection("UserCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                CartModel cartModel = documentSnapshot.toObject(CartModel.class);

                                String documentId = documentSnapshot.getId();

                                cartModel.setDocumentId(documentId);

                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        //button functions
        onBackBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CartPage.this, HomePage.class);
                startActivity(intent);
                finish();
                ;

            }
        });

        onProceedCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for checking if the cart is empty
                if (cartModelList == null || cartModelList.isEmpty()) {
                    // Displaying message if cart is empty
                    Toast.makeText(CartPage.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to checkout
                    startActivity(new Intent(CartPage.this, CheckoutPage.class));
                }
            }
        });

    }

    //calculating the total amount
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double totalBill = 0.00;

            totalBill = intent.getIntExtra("totalAmount", 0);
            String formattedprice = formatWithCommas(totalBill);
            overTotalAmount.setText("₱ " + formattedprice);
        }
    };

    //for adding commas to subtotal price
    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }
}