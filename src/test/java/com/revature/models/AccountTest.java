package com.revature.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account(2,true,2,false,true,false);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAccout_id() {
        assertEquals(2,account.getAccout_id());
    }

    @Test
    public void setAccout_id() {
    }

    @Test
    public void isBalance() {
    }

    @Test
    public void setBalance() {
    }

    @Test
    public void getCustomer_id_ref() {
        assertEquals(2,account.getAccout_id());
    }

    @Test
    public void setCustomer_id_ref() {
    }

    @Test
    public void isJoint() {
    }

    @Test
    public void setJoint() {
    }

    @Test
    public void isChecking() {
        assertEquals(true,account.isChecking());
    }

    @Test
    public void setChecking() {
    }

    @Test
    public void isSaving() {
        assertEquals(false,account.isSaving());
    }

    @Test
    public void setSaving() {
    }
}