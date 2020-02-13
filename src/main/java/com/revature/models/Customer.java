package com.revature.models;

import java.util.Objects;

public class Customer {

    private String userName;
    private int customer_ID;
    private String password;
    private String name;

    public Customer() {
        super();
    }

    public Customer(String userName,String password){
        super();
        this.userName = userName;
        this.password = password;
    }
    public Customer(String userName, int customer_ID, String password, String name) {
        super();
        this.userName = userName;
        this.customer_ID = customer_ID;
        this.password = password;
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userName='" + userName + '\'' +
                ", customer_ID=" + customer_ID +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customer_ID == customer.customer_ID &&
                Objects.equals(userName, customer.userName) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, customer_ID, password, name);
    }
}