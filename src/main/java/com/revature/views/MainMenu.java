package com.revature.views;

import com.revature.util.InputUtil;

public class MainMenu implements Views {
    @Override
    public void showMenu() {
        System.out.println("             Enter number corresponding to choice.");
        System.out.println("             1. Customer Menu");
        System.out.println("             2. Account Menu");
        System.out.println("             0. Quit");

    }

    @Override
    public Views selectionOption() {
        int selection = InputUtil.getIntInRange(0, 2);
        // User selects something - should be reusable
        // Do something with their selection, custom to this class
        switch(selection) {
            case 0: return null;
            case 1: return new CustomerView();
            case 2: return new AccountMenu();
            default: return null;
        }
    }
}
