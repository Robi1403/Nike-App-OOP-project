package com.example.nike.models;

import java.util.List;

public class OrderPageParent {

    private String orderId;
    private String numberOfItems;
    private String totalPrice;
    private String documentId;
    private List<OrderPageChild> childItemList;

    public OrderPageParent() {
    }

    public OrderPageParent(String orderId, String numberOfItems, String totalPrice, String documentId, List<OrderPageChild> childItemList) {
        this.orderId = orderId;
        this.numberOfItems = numberOfItems;
        this.totalPrice = totalPrice;
        this.documentId = documentId;
        this.childItemList = childItemList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(String numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<OrderPageChild> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<OrderPageChild> childItemList) {
        this.childItemList = childItemList;
    }
}
