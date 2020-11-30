package com.bcit.comp3717.waffle;

public class Customer {
    String customerId;
    String customerName;
    String customerPhoneNumber;
    String arriveTime;
    String restaurantName;

    public Customer() {}

    public Customer(String customerId, String customerName, String customerPhoneNumber, String arriveTime, String restaurantName) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.arriveTime = arriveTime;
        this.restaurantName = restaurantName;
    }

    public String getCustomerId() { return customerId; }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {return customerName;}

    public String getCustomerPhoneNumber() {return customerPhoneNumber;}

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getArriveTime() {return arriveTime;}

    public String getRestaurantName() {return restaurantName;}
}
