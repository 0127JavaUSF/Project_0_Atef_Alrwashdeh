package com.revature.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputUtil<stric> {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prompts user to input number in range min to max inclusive of both.
     */
    public static int getIntInRange(int min, int max) {
        int selection = 0;
        outer:
        do {
            System.out.println("           Please enter a number between " + min +
                    " and " + max);

            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                continue outer;
            }
            selection = scanner.nextInt();
            scanner.nextLine();
        } while (selection < min || selection > max);

        return selection;
    }

    public static String getNextString() {
        return scanner.nextLine();
    }

    /*public static String passwordEntry() {
        *//*String password;
        Console console = System.console();
        password = new String(console.readPassword("Please Enter password: "));
        return password;*//*
        *//*String password = "";*//*
        String password = PasswordField.readPassword("Enter password:");
        //System.out.println("Password entered was:" + password);
        return password;
    }

    static class  PasswordField {

        public static String readPassword(String prompt) {
           EraserThread et = new EraserThread(prompt);
            Thread mask = new Thread(et);
            mask.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String password = "";

            try {
                password = in.readLine();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            et.stopMasking();
            return password;
        }
    }

    static class  EraserThread implements Runnable {
        private boolean stop;

        public EraserThread(String prompt) {
            System.out.print(prompt);
        }

        public void run() {
            while (!stop) {
                System.out.print("\010*");
                try {
                    Thread.currentThread().sleep(1);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        public void stopMasking() {
            this.stop = true;
        }

    }*/
}
