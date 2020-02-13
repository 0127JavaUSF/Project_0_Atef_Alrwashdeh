package com.revature.dao;

import com.revature.models.Customer;
import com.revature.util.ConnectionUtil;
import com.revature.util.InputUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerDao {

    private static Scanner scanner = new Scanner(System.in);
    //private static String scanner;
    //public Scanner scanner = new Scanner(System.in);

public static Customer extractCustomer(ResultSet resultSet) throws SQLException{
    int customer_ID = resultSet.getInt("customer_id");
    String user_name = resultSet.getString("customer_name");
    String password = resultSet.getString("password");
    String name = resultSet.getString("name");
    return new Customer(user_name,customer_ID,password,name);
}

public static   Customer createCustomer(Customer customer){

    try(Connection connection = ConnectionUtil.getConnection()) {
        String sql = "INSERT INTO customer(name,customer_id,password,customer_name)"+
                "VALUES(?,?,?,?) RETURNING *";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,customer.getName());
        statement.setInt(2, customer.getCustomer_ID());
        statement.setString(3,customer.getPassword());
        statement.setString(4,customer.getUserName());
        ResultSet resultSet = statement.executeQuery();
        /*if (resultSet.next()){
            return extractCustomer(resultSet);
        }*/
        System.out.println("Is this Joint Account: insert Y for yes N for no");
        String joint = InputUtil.getNextString();
        //scanner.hasNextInt();
        if(joint.toUpperCase().equals("N")) createAccount(customer);
        else if (joint.toUpperCase().equals("Y")) createJoinAccount(customer);
        }catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
    }

    /**
     * Get the account number and account ID from customer
     */
    //create new account

    /**
     *
     * @param
     * @throws SQLException
     */
    public static void createAccount(Customer customer) throws SQLException {

    //All fields needed in to create account
        int accountID =0;
        int customer_id = 0;
        boolean cheking;
        boolean saving = false;
        System.out.println("Enter the account ID number: ");
        int accoutID = scanner.nextInt();
        System.out.println("Enter Account Balance: ");
        double balance = scanner.nextDouble();
        System.out.println("Is this Checking Account Y for Yes and N for No");
        String accountType = scanner.next();
        if (accountType.toUpperCase().equals("Y")){
            cheking = true;
        } else {cheking = false; saving =true;}
        /*if (resultSet.next()) {
            customer_id = resultSet.getInt("customer_id"); //customer id from created user.
        }*/
        try (Connection connection = ConnectionUtil.getConnection() ){
            String sql = "SELECT customer_id FROM customer where customer_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,customer.getUserName());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                customer_id = resultSet.getInt("customer_id");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
//JDBC connection
        try (Connection connection = ConnectionUtil.getConnection() ){
            String sql = "INSERT INTO account(account_id, balance,customer_id_ref,cheking,saving)"+
                    "VALUES(?,?,?,?,?) RETURNING *";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,accoutID);
            statement.setDouble(2,balance);
            statement.setInt(3,customer_id);
            statement.setBoolean(4,cheking);
            statement.setBoolean(5,saving);
            ResultSet resultSet1 = statement.executeQuery();
            if (resultSet1.next()){
                accountID = resultSet1.getInt("account_id");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //int accountID1 = accountID;
        //return accountID;
    }

    //create joint account
    public static void createJoinAccount(Customer customer) throws SQLException {
        int accountID = 0;

         System.out.println("What is The account number associate with your account: ");
         accountID = InputUtil.getIntInRange(1,Integer.MAX_VALUE);
        //find if the account number in the account table

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "SELECT account_id FROM account where account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,accountID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int account = resultSet.getInt("account_id");
                if (accountID != account) {
                    System.out.println("This account is not avilable chose different account");
                    System.out.println("Enter new AccountID: ");
                    accountID = InputUtil.getIntInRange(1,Integer.MAX_VALUE);
                }

                //createCustomer(Customer )
            }



        } catch (SQLException e) {
            e.printStackTrace();

        }

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO customer_accounts(customer_id,account_id)"+
                    "VALUES(?,?) RETURNING *";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,customer.getCustomer_ID());
            statement.setInt(2,accountID);

            ResultSet resultSet1 = statement.executeQuery();

        } catch (SQLException e) {
            System.out.println("No value inside the account table");
            createJoinAccount(customer);
        }
        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "UPDATE account SET joint = true WHERE account_id = " + accountID;
            PreparedStatement statement = connection.prepareStatement(sql);
            //tatement.setInt(1,accountID);
            int updatequery = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "UPDATE customer SET parent_account = " + accountID + " where customer_id = " + customer.getCustomer_ID() ;
            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setInt(1,customer.getCustomer_ID());
            //statement.setInt(2,accountID);

            int resultSet1 = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static int LogIn(Customer customer){
        //System.out.println(customer.getCustomer_ID());
        int cutomer_id = 0;
    boolean checkValidation;
    try(Connection connection = ConnectionUtil.getConnection()) {
        String sql = "SELECT customer_id, customer_name, password FROM customer "+
                "WHERE customer_name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,customer.getUserName());
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()) {
            String user = resultSet.getString("customer_name");
            String passwordfromtable = resultSet.getString("password");
             cutomer_id = resultSet.getInt("customer_id");
            //System.out.println(user+ " "+customer.getPassword());
            if(passwordfromtable.equals(customer.getPassword())){
                System.out.println("Hello: " + customer.getUserName().toUpperCase());
                return cutomer_id;
            } else {
                return 0;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
    }
}
