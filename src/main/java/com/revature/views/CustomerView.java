package com.revature.views;

import com.revature.dao.CustomerDao;
import com.revature.models.Customer;
import com.revature.util.InputUtil;

public class CustomerView implements Views {
    public Customer customer;
    /*private static String userName;
    private static String password;*/
    @Override
    public void showMenu() {
        System.out.println("           1. Register Account");
        //System.out.println("2. Login to Account");
        System.out.println("           0. Back");
    }

    @Override
    public Views selectionOption() {
        int selection = InputUtil.getIntInRange(0, 1);
        switch (selection) {
            case 1: registerCustomer(); return this;
            //case 2: login(); return this;
            case 3: ; return this;
            default:
            case 0: return new MainMenu();
        }
    }

public void registerCustomer(){

    System.out.println("Enter Account User_name: ");
    String userName = InputUtil.getNextString();
    System.out.println("Enter Password: ");
    String password = InputUtil.getNextString();
    System.out.println("Enter you're Customer ID: ");
    int customerID = InputUtil.getIntInRange(1,Integer.MAX_VALUE);
   // System.out.println(password);
    System.out.println("Enter your account holder name: ");
    String name = InputUtil.getNextString();

    customer = new Customer(userName,customerID,password,name);
    //customer = CustomerDao.
    customer = CustomerDao.createCustomer(customer);
    }
     public int login(){
        int customer_id;
        Customer check;
         System.out.println("Enter you credentials: ");
         System.out.println("Enter user_name");
         String userName = InputUtil.getNextString();
         System.out.println("Enter password: ");
         String password = InputUtil.getNextString();
         Customer customer = new Customer(userName,password);
         customer_id =CustomerDao.LogIn(customer);
         /*if (check == customer){
                new AccountMenu().showMenu();
                new AccountMenu().selectionOption();

         }*/

         return customer_id;
     }



}
