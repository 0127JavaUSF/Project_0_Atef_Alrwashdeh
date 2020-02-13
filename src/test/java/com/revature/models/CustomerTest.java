package com.revature.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("atef",125,"123","Atef");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUserName() {
        assertEquals("atef",customer.getUserName());
    }

    @Test
    public void getCustomer_ID() {
        assertEquals(125,customer.getCustomer_ID());
    }

    @Test
    public void getPassword() {
        assertEquals("123",customer.getPassword());
    }

    @Test
    public void getName() {
        assertEquals("Atef",customer.getName());
    }
}