package com.venherak.lab2;

import com.venherak.lab2.automate.Automate;
public class Main {
    public static void main(String args[]) {

        Automate automate = new Automate();

        System.out.println(automate.toString());

        String[] signals = {"dlm", "ltr", "ltr", "dlm", "ltr", "ltr", "ltr"};

        try {
            automate.performSignalArray(signals);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(automate.toString());

        try {
            automate.performSignal("ltr");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(automate.toString());
    }
}
