package com.example.nike.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nike.Adapters.OrderPageParentAdapter;
import com.example.nike.models.OrderPageChild;
import com.example.nike.models.OrderPageParent;
import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderPage extends AppCompatActivity {

    OrderPageParentAdapter parentItemAdapter;
    List<OrderPageParent> parentItemList;
    List<OrderPageChild> childItemList;

    RecyclerView orderpage;

    Button onBackBtn5;

    FirebaseFirestore database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        orderpage = findViewById(R.id.ordersView);
        orderpage.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        onBackBtn5 = findViewById(R.id.backBtn5);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        parentItemList = new ArrayList<>();
        childItemList = new ArrayList<>();

        parentItemAdapter = new OrderPageParentAdapter(OrderPage.this, parentItemList, childItemList);

        onBackBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        //fetch parent data which contains the number of items,order number ,and total price
        database.collection("UserActivity").document(auth.getCurrentUser().getUid())
                .collection("UserOrders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        parentItemList.clear();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                OrderPageParent parentItem = documentSnapshot.toObject(OrderPageParent.class);
                                String documentId = documentSnapshot.getId();

                                parentItem.setDocumentId(documentId);

                                // Clear the childItemList for each parent
                                childItemList.clear();

                                //fetch the sub Item inside the parent data, fetches the orders of the useer
                                database.collection("UserActivity").document(auth.getCurrentUser().getUid())
                                        .collection("UserOrders").document(documentId).collection(documentId)
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (DocumentSnapshot childDocument : task.getResult().getDocuments()) {
                                                        OrderPageChild childItem = childDocument.toObject(OrderPageChild.class);
                                                        String subdocumentId = childDocument.getId();

                                                        childItem.setDocumentId(subdocumentId);
                                                        childItemList.add(childItem);
                                                    }

                                                    // Add the childItemList to the parent item
                                                    parentItem.setChildItemList(new ArrayList<>(childItemList));
                                                    parentItemList.add(parentItem);

                                                    // Update the UI or perform any other operations after fetching both parent and child documents
                                                    orderpage.setAdapter(parentItemAdapter);
                                                    parentItemAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }
}