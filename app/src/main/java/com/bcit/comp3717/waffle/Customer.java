package com.bcit.comp3717.waffle;

public class Customer {
    String customerId;
    String customerFirstName;
    String customerLastName;
    String customerPhoneNumber;
    String customerUserName;

    public Customer() {}

    public Customer(String customerId, String customerFirstName,
                   String customerLastName, String customerPhoneNumber, String customerUserName) {

        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerUserName = customerUserName;
    }

    public String getCustomerId() { return customerId; }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {return customerFirstName;}

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {return customerLastName;}

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    public String getCustomerPhoneNumber() {return customerPhoneNumber;}

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerUserName() {return customerUserName;}

    public void setCustomerUserName(String customerUserName) {
        this.customerUserName = customerUserName;
    }
}
