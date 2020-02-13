package com.revature.dao;

import com.revature.models.Customer;
import com.revature.util.ConnectionUtil;
import com.revature.views.MainMenu;
import com.revature.views.Views;
import sun.font.CreatedFontTracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountDao {
    private Scanner scanner = new Scanner(System.in);

    /*public static int AcntNumberfromAccountandcustomer(int customerid){


    }*/

    public static int acnnumberfromaccounttablejointaccount(int customerID){
        int accountID = 0;
        //sql connection

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "select parent_account from customer where customer_id = " + customerID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                accountID = resultSet.getInt("parent_account");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return accountID;
    }


    public static double checkBalance(int customer_id)  {
        double balance = 0;
        int parent_id = acnnumberfromaccounttablejointaccount(customer_id);
            if (parent_id ==0) {
                try (Connection connection = ConnectionUtil.getConnection()) {
                    String sql = "SELECT balance FROM account WHERE customer_id_ref = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, customer_id);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        balance = resultSet.getDouble("balance");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        //for joint account
        else {
            try (Connection connection = ConnectionUtil.getConnection()){
                String sql = "SELECT balance FROM account WHERE account_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, parent_id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    balance = resultSet.getDouble("balance");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return balance;
    }

    public  static void Withdraw(double balance,int customerID) {
        int parentaccountnumnber = acnnumberfromaccounttablejointaccount(customerID);
        if (parentaccountnumnber == 0) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                String sql = "UPDATE account SET balance =" + balance + "WHERE customer_id_ref = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, customerID);
                int executeUpdate = statement.executeUpdate();
                //System.out.println(executeUpdate);
                balance = checkBalance(customerID);
                System.out.println("The remaining balance is: " + balance);
                System.out.println("*******************************");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //joint account
            //return balance;
        }
        else {
            try (Connection connection = ConnectionUtil.getConnection()) {
                String sql = "UPDATE account SET balance =" + balance + "WHERE customer_id_ref = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, parentaccountnumnber);
                int executeUpdate = statement.executeUpdate();
                //System.out.println(executeUpdate);
                balance = checkBalance(customerID);
                System.out.println("The remaining balance is: " + balance);
                System.out.println("*******************************");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addBalance(double newBalance,int customerID){
        int parenAccountNumber= acnnumberfromaccounttablejointaccount(customerID);

        if (parenAccountNumber == 0){
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE account SET balance =" + newBalance + "WHERE customer_id_ref = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,customerID);
            int execuateUpdate = statement.executeUpdate();
            newBalance = checkBalance(customerID);
            System.out.println("The new Balance is: "+ newBalance);
            System.out.println("*******************************");
        }catch (SQLException e){
            e.printStackTrace();
        }}
        else {
            try (Connection connection = ConnectionUtil.getConnection()) {
                String sql = "UPDATE account SET balance =" + newBalance + "WHERE customer_id_ref = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,parenAccountNumber);
                int execuateUpdate = statement.executeUpdate();
                newBalance = checkBalance(customerID);
                System.out.println("The new Balance is: "+ newBalance);
                System.out.println("*******************************");
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
    private static int customerID(int accountID, int customerid){
        int cusomerID =0;
        int orginalcustomerid = customerid;
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql= "SELECT customer_id_ref FROM account WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,accountID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                cusomerID = resultSet.getInt("customer_id_ref");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        if (cusomerID > 0){
            return cusomerID;
        }
        else {
        return orginalcustomerid;}
    }

public static void Transfer(int customerID,int transferToAccountnumber,double amountTransfer){
        int parentAccount = acnnumberfromaccounttablejointaccount(customerID);
        int firstAccountNumber = 0;
        if(parentAccount != 0) {customerID = parentAccount;}
        //get info from account

        try(Connection connection = ConnectionUtil.getConnection()) {
        String sql= "SELECT account_id FROM account WHERE customer_id_ref = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,customerID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
                firstAccountNumber = resultSet.getInt("account_id");
        }
    } catch (SQLException e){
        e.printStackTrace();
    }
    //customerID for transfered account
    int customerID2 = customerID(transferToAccountnumber,customerID);

    //The amount in first account
    double balnaceFirstAccount = checkBalance(customerID);
    //The amouunt in 2nd account
    double balanceSecondAccount = 0;
    //check


    if(balnaceFirstAccount < amountTransfer) {
        System.out.println("There is no suffecient fund");
    } else {
        balnaceFirstAccount-=amountTransfer;
         balanceSecondAccount = checkBalance(customerID2);
        balanceSecondAccount+=amountTransfer;
    }

    System.out.println("Your new balance is: "+balnaceFirstAccount);
    System.out.println("*******************************");
    //System.out.println(balanceSecondAccount);

    try (Connection connection = ConnectionUtil.getConnection()){

        String sql = "BEGIN; "+
                " UPDATE account SET balance = " + balnaceFirstAccount + "WHERE customer_id_ref = ?;"+
                " UPDATE account SET balance = " + balanceSecondAccount + "WHERE customer_id_ref = ?;"+
                " COMMIT;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,customerID);
        statement.setInt(2,customerID2);
        int execuateUpdate = statement.executeUpdate();

    } catch (SQLException e){
        e.printStackTrace();
    }


    }

    public static Views deleteAcount(int customerID){
        int accountID = 0;
        int parentAccount = acnnumberfromaccounttablejointaccount(customerID);
        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "SELECT account_id from account where joint = 'true' and customer_id_ref =" + customerID;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                accountID = resultSet.getInt("account_id");
                //System.out.println(accountID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        //joint account
        if (parentAccount !=0){
            try (Connection connection = ConnectionUtil.getConnection()){
                String sql3 = "delete from customer_accounts where account_id = "+ parentAccount;
                PreparedStatement statement = connection.prepareStatement(sql3);
                int resultSet = statement.executeUpdate();


            }catch (SQLException e){
                System.out.println("No joint account");
            }
        }


        try (Connection connection = ConnectionUtil.getConnection()){
            String sql="DELETE FROM account WHERE customer_id_ref =" + customerID;
            String sql2 = "DELETE FROM customer WHERE customer_id =" + customerID;
            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement1 = connection.prepareStatement(sql2);
            int resultSet = statement.executeUpdate();
            int resultSet1 = statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("See you.....");
        return null;
    }



}
