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
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.models.CartModel;
import com.example.nike.Adapters.CheckoutAdapter;
import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CheckoutPage extends AppCompatActivity {
    Button onBackBtn3, onCheckoutBtn2; //for declaration

    //for database
    FirebaseFirestore db;
    FirebaseAuth auth;

    //total amount declaration
    TextView overTotalAmount;
    TextView overTotalAmount2;

    RecyclerView recyclerView; //recycler view declaration
    CheckoutAdapter checkoutAdapter;
    List<CartModel> checkoutModelList;
    double totalBill = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //linking buttons to button ids
        onCheckoutBtn2 = findViewById(R.id.checkoutBtn2);
        onBackBtn3 = findViewById(R.id.backBtn3);

        //for adding the data to database
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView_Checkout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        checkoutModelList = new ArrayList<>();
        checkoutAdapter = new CheckoutAdapter(this, checkoutModelList);
        recyclerView.setAdapter(checkoutAdapter);

        //for computing total amount
        overTotalAmount = findViewById(R.id.subTotal);
        overTotalAmount2 = findViewById(R.id.finalTotal);

        //displaying the total amount
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver2, new IntentFilter("MyTotalAmount"));

        //for adding the data to database
        db.collection("UserActivity").document(auth.getCurrentUser().getUid())
                .collection("UserCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                CartModel cartModel = documentSnapshot.toObject(CartModel.class);



                                checkoutModelList.add(cartModel);
                                checkoutAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        //button functions
        onBackBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CheckoutPage.this, CartPage.class);
                startActivity(intent);
                finish();
                ;

            }
        });

        onCheckoutBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();


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

    public BroadcastReceiver mMessageReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalBill = intent.getIntExtra("totalAmount", 0);
            String formattedprice = formatWithCommas(totalBill);
            overTotalAmount2.setText("₱ " + formattedprice);
        }
    };

    //for adding commas to subtotal price
    public static String formatWithCommas(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        return formatter.format(number);
    }

    private void saveDataToDatabase(){
        //for firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        String orderNumber = orderIdGenerator();
        String items = String.valueOf(checkoutModelList.size());
        double total = totalBill;


        final String[] documentId = {""};

        final HashMap<String, Object> userOrder = new HashMap<>();
        userOrder.put("orderId", orderNumber.toString());
        userOrder.put("numberOfItems", items.toString());
        userOrder.put("totalPrice", String.valueOf(total));

        db.collection("UserActivity").document(auth.getCurrentUser().getUid()).collection("UserOrders").add(userOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentId[0] = documentReference.getId();
                        System.out.println(documentId[0]);

                        if (checkoutModelList != null && checkoutModelList.size() > 0) {
                            for (CartModel model : checkoutModelList) {
                                String ProductId = model.getProductId();
                                int Stock = model.getStocks();
                                Stock = Stock - 1;

                                db.collection("AllProducts").document(ProductId).update("Stocks", Stock);
                                final HashMap<String, Object> cartMap = new HashMap<>();

                                cartMap.put("productName", model.getProductName());
                                cartMap.put("productPrice", model.getProductPrice());
                                cartMap.put("productImage", model.getProductImage());
                                cartMap.put("productSize", model.getProductSize());

                                db.collection("UserActivity").document(auth.getCurrentUser().getUid())
                                        .collection("UserOrders").document(documentId[0]).collection(documentId[0])
                                        .add(cartMap)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentReference> task) {
                                                Intent intent = new Intent(CheckoutPage.this, OrderConfirmPage.class);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(CheckoutPage.this, "Your order has been placed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                    }
                });



        // deleting the items in the cart after the check out
        db.collection("UserActivity").document(auth.getCurrentUser().getUid())
                .collection("UserCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                documentSnapshot.getReference().delete();
                            }
                        }
                    }
                });

    }
    public static String orderIdGenerator() {
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(currentDate);

        Random random = new Random();
        int randomNumber = random.nextInt(10000);

        String uniqueId = formattedDate + randomNumber;

        return uniqueId;
    }

    void updateStock(){

    }
}