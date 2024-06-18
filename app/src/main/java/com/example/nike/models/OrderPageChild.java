package com.example.nike.models;

public class OrderPageChild {
    String ProductName;
    int ProductPrice;
    String ProductImage;

    String productSize;

    public OrderPageChild(String productName, int productPrice, String productImage, String productSize) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductImage = productImage;
        this.productSize = productSize;
    }

    public OrderPageChild() {
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

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }


    public void setDocumentId(String subdocumentId) {
    }
}
