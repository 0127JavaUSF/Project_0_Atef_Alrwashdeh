package com.revature;

import com.revature.views.MainMenu;
import com.revature.views.Views;

public class App {
    public static void main(String[] args) {
        System.out.println("                  *************************");
        System.out.println("                 **This Fresno Bulldog Bank**              ");
        System.out.println("                  *************************");
        Views view = new MainMenu();

        while(view != null) {
            ;
            view.showMenu();
            view = view.selectionOption();
        }

        System.out.println("Bye!");
    }
}
