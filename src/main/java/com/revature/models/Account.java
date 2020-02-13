package com.revature.models;

import java.util.Objects;

public class Account {
    private int accout_id;
    private boolean balance;
    private int customer_id_ref;
    private boolean joint;
    private boolean checking;
    private boolean saving;

    public Account(int accout_id, boolean balance, int customer_id_ref, boolean joint, boolean checking, boolean saving) {
        super();
        this.accout_id = accout_id;
        this.balance = balance;
        this.customer_id_ref = customer_id_ref;
        this.joint = joint;
        this.checking = checking;
        this.saving = saving;
    }



    public int getAccout_id() {
        return accout_id;
    }

    public void setAccout_id(int accout_id) {
        this.accout_id = accout_id;
    }

    public boolean isBalance() {
        return balance;
    }

    public void setBalance(boolean balance) {
        this.balance = balance;
    }

    public int getCustomer_id_ref() {
        return customer_id_ref;
    }

    public void setCustomer_id_ref(int customer_id_ref) {
        this.customer_id_ref = customer_id_ref;
    }

    public boolean isJoint() {
        return joint;
    }

    public void setJoint(boolean joint) {
        this.joint = joint;
    }

    public boolean isChecking() {
        return checking;
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }

    public boolean isSaving() {
        return saving;
    }

    public void setSaving(boolean saving) {
        this.saving = saving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accout_id == account.accout_id &&
                balance == account.balance &&
                customer_id_ref == account.customer_id_ref &&
                joint == account.joint &&
                checking == account.checking &&
                saving == account.saving;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accout_id, balance, customer_id_ref, joint, checking, saving);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accout_id=" + accout_id +
                ", balance=" + balance +
                ", customer_id_ref=" + customer_id_ref +
                ", joint=" + joint +
                ", checking=" + checking +
                ", saving=" + saving +
                '}';
    }
}
