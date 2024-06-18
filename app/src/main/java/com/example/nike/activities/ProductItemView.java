package com.example.nike.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nike.R;
import com.example.nike.models.productModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProductItemView extends AppCompatActivity {

    Button onBackBtn, onBagBtn,onAddToBagBtn;
    Button Size6,Size65,Size7,Size75,Size8,Size85,Size9,Size95,Size10,Size105,Size11,Size115,selectedButton;

    com.example.nike.models.productModel productModel;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

     String size = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item_view);

        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        int productPrice = intent.getIntExtra("productPrice",0);
        String productImage = intent.getStringExtra("productImage");
        String productDescription = intent.getStringExtra("productDescription");
        int Stocks = intent.getIntExtra("Stocks",0);
        String DocumentId = intent.getStringExtra("DocumentId");


        productModel = new productModel(productName, productPrice, productImage, productDescription,Stocks,DocumentId);

        ImageView imageView = findViewById(R.id.productViewImage);
        TextView nameTextView = findViewById(R.id.productViewName);
        TextView priceTextView = findViewById(R.id.productViewPrice);
        TextView description = findViewById(R.id.productViewDescription);
        TextView stocks = findViewById(R.id.StocksLeft);

        //sets the information about the products in the frontend
        Glide.with(this).load(productImage).into(imageView);
        nameTextView.setText(productName);
        priceTextView.setText("₱ "+productPrice);
        description.setText(productDescription);
        stocks.setText("Stock Available: " + Stocks);

        onBackBtn = findViewById(R.id.backBtn);
        onBagBtn = findViewById(R.id.bagBtn);
        onAddToBagBtn = findViewById(R.id.addToBagBtn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();



        shoeSize();

        onBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductItemView.this, HomePage.class);
                startActivity(intent);
                finish();;

            }
        });

        onBagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductItemView.this, CartPage.class);
                startActivity(intent);
                finish();;

            }
        });

        onAddToBagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Stocks == 0){
                    Toast.makeText(ProductItemView.this, "Item out of Stock", Toast.LENGTH_SHORT).show();
                }else {
                    addedToCart();
                }


            }
        });

    }

    private void addedToCart(){
        //this function saves the data to the database
        final HashMap<String,Object> cartMAp = new HashMap<>();

        cartMAp.put("productName",productModel.getProductName());
        cartMAp.put("productPrice",productModel.getProductPrice());
        cartMAp.put("productImage",productModel.getProductImage());
        cartMAp.put("productSize", size.toString());
        cartMAp.put("Stocks", productModel.getStocks());
        cartMAp.put("ProductId", productModel.getDocumentId());


        if (!TextUtils.isEmpty(size)){
            firestore.collection("UserActivity").document(auth.getCurrentUser().getUid())
                    .collection("UserCart").add(cartMAp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(ProductItemView.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }else{
            Toast.makeText(this, "Select Size", Toast.LENGTH_SHORT).show();
        }
        
    }

    void shoeSize(){
        // this function listens to what size the user chooses
        Size6 = findViewById(R.id.sizeBtnSize6);
        Size65 = findViewById(R.id.sizeBtnSize65);
        Size7 = findViewById(R.id.sizeBtnSize7);
        Size75 = findViewById(R.id.sizeBtnSize75);
        Size8 = findViewById(R.id.sizeBtnSize8);
        Size85 = findViewById(R.id.sizeBtnSize85);
        Size9 = findViewById(R.id.sizeBtnSize9);
        Size95 = findViewById(R.id.sizeBtnSize95);
        Size10 = findViewById(R.id.sizeBtnSize10);
        Size105 = findViewById(R.id.sizeBtnSize105);
        Size11 = findViewById(R.id.sizeBtnSize11);
        Size115 = findViewById(R.id.sizeBtnSize115);

        Size6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size6);
                size = "6";
            }
        });

        Size65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size65);
                size = "6.5";
            }
        });

        Size7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size7);
                size = "7";
            }
        });

        Size75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size75);
                size = "7.5";
            }
        });

        Size8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size8);
                size = "8";
            }
        });

        Size85.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size85);
                size = "8.5";
            }
        });

        Size9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size9);
                size = "9";
            }
        });

        Size95.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size95);
                size = "9.5";
            }
        });

        Size10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size10);
                size = "10";
            }
        });

        Size105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size105);
                size = "10.5";
            }
        });

        Size11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size11);
                size = "11";
            }
        });

        Size115.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(Size115);
                size = "11.5";
            }
        });

    }

    //for changing the color of buttons upon clicking
    void onButtonClick(Button clickedButton) {
        if (selectedButton == clickedButton) {
            selectedButton.setBackgroundResource(R.drawable.bbutton);
            selectedButton.setTextColor(Color.BLACK);
            selectedButton = null;
        } else {

            if (selectedButton != null) {
                selectedButton.setBackgroundResource(R.drawable.bbutton);
                selectedButton.setTextColor(Color.BLACK);
            }

            clickedButton.setBackgroundResource(R.drawable.bbutton_clicked);
            clickedButton.setTextColor(Color.WHITE);

            selectedButton = clickedButton;
        }
    }

}