package com.example.nike.models;

import java.io.Serializable;

public class CartModel implements Serializable {

    String productImage;
    String productName;
    int productPrice;
    String productSize;
    String documentId;

    int Stocks;
    String ProductId;

    public CartModel () {
    }

    public CartModel(String productImage, String productName, int productPrice, String productSize, String documentId, int stocks, String productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.documentId = documentId;
        Stocks = stocks;
        ProductId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getStocks() {
        return Stocks;
    }

    public void setStocks(int stocks) {
        Stocks = stocks;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }
}