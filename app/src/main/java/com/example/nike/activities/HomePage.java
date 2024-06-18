package com.example.nike.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nike.R;
import com.example.nike.Adapters.productAdapter;
import com.example.nike.models.productModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {


    com.example.nike.Adapters.productAdapter productAdapter, allproductAdapter;
    List<productModel> productModelList, allProductList;

    RecyclerView popularSection, productFeed;
    FirebaseFirestore database, allProducts;

    Button onAll,onShoes,onSlides,onProfileBtn,onBagBtn,onOrdersBtn,onHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        popularSection = findViewById(R.id.PopularSection);
        productFeed = findViewById(R.id.feed);

        onAll = findViewById(R.id.AllButton);
        onShoes = findViewById(R.id.ShoesButton);
        onSlides = findViewById(R.id.SlidesButton);
        onProfileBtn = findViewById(R.id.profileBtn);
        onBagBtn = findViewById(R.id.homebagBtn);
        onOrdersBtn = findViewById(R.id.ordersBtn);

        database = FirebaseFirestore.getInstance();
        allProducts = FirebaseFirestore.getInstance();

        popularSection.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        productFeed.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));


        productModelList = new ArrayList<>();
        allProductList = new ArrayList<>();
        productAdapter = new productAdapter(this, productModelList);
        allproductAdapter = new productAdapter(this, allProductList);

        popularSection.setAdapter(productAdapter);
        productFeed.setAdapter(allproductAdapter);

        //fetch data for popular section in home page
        database.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productModel productModel = document.toObject(com.example.nike.models.productModel.class);
                                productModel.setDocumentId(document.getId());
                                productModelList.add(productModel);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(HomePage.this, "An error occurred" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        viewAll();

        onAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(onAll);
                viewAll();// fetch all products
            }
        });

        onShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(onShoes);
                viewShoes();// fetch shoes only
            }
        });

        onSlides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(onSlides);
                viewSlides();// fetch slides only
            }
        });

        //functions for navigation bar
        onOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, OrderPage.class);
                startActivity(intent);
                finish();;
            }
        });

        onBagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, CartPage.class);
                startActivity(intent);
                finish();;
            }
        });

        onProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage.this, ProfilePage.class);
                startActivity(intent);
                finish();;

            }
        });

        productAdapter.setOnItemClickListener(new productAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(productModel product) {
                //sets the data to be transfered or copied in the next activity
                Intent intent = new Intent(HomePage.this, ProductItemView.class);
                intent.putExtra("productName", product.getProductName());
                intent.putExtra("productPrice", product.getProductPrice());
                intent.putExtra("productImage", product.getProductImage());
                intent.putExtra("productDescription", product.getProductDescription());
                intent.putExtra("Stocks", product.getStocks());
                intent.putExtra("DocumentId", product.getDocumentId());
                startActivity(intent);
            }
        });
    }


    void viewAll() {
        allProductList.clear();

        allProducts.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productModel productfeed = document.toObject(productModel.class);
                                productfeed.setDocumentId(document.getId());
                                allProductList.add(productfeed);
                                allproductAdapter.notifyDataSetChanged();
                            }

                            // Set item click listener for allproductAdapter
                            allproductAdapter.setOnItemClickListener(new productAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(productModel product) {
                                    Intent intent = new Intent(HomePage.this, ProductItemView.class);
                                    intent.putExtra("productName", product.getProductName());
                                    intent.putExtra("productPrice", product.getProductPrice());
                                    intent.putExtra("productImage", product.getProductImage());
                                    intent.putExtra("productDescription", product.getProductDescription());
                                    intent.putExtra("Stocks", product.getStocks());
                                    intent.putExtra("DocumentId", product.getDocumentId());
                                    startActivity(intent);
                                }
                            });

                        } else {
                            Toast.makeText(HomePage.this, "An error occurred" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    void viewShoes() {
        allProductList.clear(); // Clear the list before adding new items

        allProducts.collection("AllProducts").whereEqualTo("Category", "shoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        productModel productfeed = document.toObject(productModel.class);
                        productfeed.setDocumentId(document.getId());
                        allProductList.add(productfeed);
                    }

                    allproductAdapter.notifyDataSetChanged();

                    allproductAdapter.setOnItemClickListener(new productAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(productModel product) {
                            Intent intent = new Intent(HomePage.this, ProductItemView.class);
                            intent.putExtra("productName", product.getProductName());
                            intent.putExtra("productPrice", product.getProductPrice());
                            intent.putExtra("productImage", product.getProductImage());
                            intent.putExtra("productDescription", product.getProductDescription());
                            intent.putExtra("Stocks", product.getStocks());
                            intent.putExtra("DocumentId", product.getDocumentId());
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(HomePage.this, "An error occurred" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void viewSlides() {
        allProductList.clear(); // Clear the list before adding new items

        allProducts.collection("AllProducts").whereEqualTo("Category", "slides").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        productModel productfeed = document.toObject(productModel.class);
                        productfeed.setDocumentId(document.getId());
                        allProductList.add(productfeed);
                    }

                    allproductAdapter.notifyDataSetChanged();


                    allproductAdapter.setOnItemClickListener(new productAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(productModel product) {
                            Intent intent = new Intent(HomePage.this, ProductItemView.class);
                            intent.putExtra("productName", product.getProductName());
                            intent.putExtra("productPrice", product.getProductPrice());
                            intent.putExtra("productImage", product.getProductImage());
                            intent.putExtra("productDescription", product.getProductDescription());
                            intent.putExtra("Stocks", product.getStocks());
                            intent.putExtra("DocumentId", product.getDocumentId());
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(HomePage.this, "An error occurred" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void onButtonClick(Button clickedButton) {
        onAll.setBackgroundResource(R.drawable.bbutton);
        onAll.setTextColor(Color.BLACK);

        onShoes.setBackgroundResource(R.drawable.bbutton);
        onShoes.setTextColor(Color.BLACK);

        onSlides.setBackgroundResource(R.drawable.bbutton);
        onSlides.setTextColor(Color.BLACK);

        clickedButton.setBackgroundResource(R.drawable.bbutton_clicked);
        clickedButton.setTextColor(Color.WHITE);
    }


}