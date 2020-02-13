package com.revature.views;

import com.revature.dao.AccountDao;
import com.revature.dao.CustomerDao;
import com.revature.models.Customer;
import com.revature.util.ConnectionUtil;
import com.revature.util.InputUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountMenu implements Views {
    //Customer customer;
    int customerID = new CustomerView().login();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void showMenu() {
        //System.out.println("Create Account");
        System.out.println("           1. Check Balance");
        System.out.println("           2. Withdarw from Balance");
        System.out.println("           3. Add Balance");
        System.out.println("           4. Transfer to new Account");
        System.out.println("           5. Close an account");
        System.out.println("           6. Logout");
        //System.out.println("0. Back");
    }

    @Override
    public Views selectionOption() {
        int selection = InputUtil.getIntInRange(0, 6);
        switch (selection) {
            case 1:
                if (customerID == 0) {
                    System.out.println("You entered wrong credintials, Please try again");
                    return new MainMenu();}
                check_balance();return this;
            case 2:
                //if (customerID == 0) return new MainMenu();
                if (customerID == 0) {
                    System.out.println("You entered wrong credintials, Please try again");
                    return new MainMenu();}
                System.out.println("How much you need to Withdraw: ");
                double withdrwabalance = scanner.nextDouble();
                withDraw(withdrwabalance, customerID);
                return this;
            case 3:
                //if (customerID == 0) return new MainMenu();
                if (customerID == 0) {
                    System.out.println("You entered wrong credintials, Please try again");
                    return new MainMenu();}
                System.out.println("How much you want to add to your balance: ");
                double add = scanner.nextDouble();
                addBalnce(add,customerID);
                 return this;
            case 4:
                if (customerID == 0) {
                    System.out.println("You entered wrong credintials, Please try again\n");
                    return new MainMenu();}
                //if (customerID == 0) return new MainMenu();
                Transfer(customerID);
                return this;
            case 6:
                //logout();
                return new MainMenu();
            case 5:
                deleteAccount(customerID);
                return new MainMenu();

            default:return new MainMenu();
            //case 0:
        }
    }


    public double check_balance(){
        //CustomerDao dao;
        //Customer customer = CustomerDao.extractCustomer()
        //int customer_id;
        /*System.out.println("What is your account ID: ");
        int accountID = InputUtil.getIntInRange(1,Integer.MAX_VALUE);
        //customer.getCustomer_ID();
        System.out.println("This is the your balance: ");*/
        //customer_id =new CustomerView().login();
        //System.out.println(customerID);
        double balance = AccountDao.checkBalance(customerID);
        System.out.println("Yor balance is: "+ balance);
        System.out.println("*******************************");
        return balance;
    }

    public void withDraw(double withdrwas,int customer_ID) {

        double balance = check_balance();
        if (withdrwas < 0.0d){
            System.out.println("You can't withdrwa negative amount ");

            //throw new IllegalArgumentException("You can't withdraw negative amount of money");
        }
       else if (withdrwas > balance) {
            System.out.println("This operation not valid");
        } else if (withdrwas <= balance){
                balance = balance - withdrwas;
        }
       //update the Amount in the tables JDBC
       AccountDao.Withdraw(balance,customer_ID);

    }
    public void addBalnce(double addbalnce,int customerID){
        double balance = check_balance();
        if (addbalnce < 0){
            System.out.println("You can't add negative numbers");
        }else if (addbalnce> 0){
            balance = balance + addbalnce;
        }
        AccountDao.addBalance(balance,customerID);

    }
    public Views logout(){
        return new MainMenu();
    }

    //Transfer account
    public void Transfer(int customerID){

        System.out.print("What the account number you want to transfer to: ");
        int account_number = InputUtil.getIntInRange(1,Integer.MAX_VALUE);
        System.out.println("Enter the amount you want to transfer: ");
        double amountTotransfer = scanner.nextDouble();
        AccountDao.Transfer(customerID,account_number,amountTotransfer);


    }

    public void deleteAccount(int customerID){
        System.out.println("Are you sure you want to drop you account: ");
        String dropRecord = InputUtil.getNextString();
        //AccountDao.AcntNumberfromAccountandcustomer(customerID);
        if (dropRecord.toUpperCase().equals("Y")){
            AccountDao.deleteAcount(customerID);
        }
        //new MainMenu();

    }


}
