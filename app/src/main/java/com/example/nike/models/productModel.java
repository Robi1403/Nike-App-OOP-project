package com.example.nike.models;

import java.io.Serializable;

public class productModel implements Serializable {
    String ProductName;
    int ProductPrice;
    String ProductImage;
    String ProductDescription;
    int Stocks;
    String DocumentId;

    public productModel() {
    }

    public productModel(String productName, int productPrice, String productImage, String productDescription, int stocks, String documentId) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductImage = productImage;
        ProductDescription = productDescription;
        Stocks = stocks;
        DocumentId = documentId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public int getStocks() {
        return Stocks;
    }

    public void setStocks(int stocks) {
        Stocks = stocks;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }
}
