package com.venherak.lab1;

import java.util.*;

public class Main {

        public static void main(String[] args) {
            LabList list = new LabList();
            list.add(new Entry("Diatlov", 89));
            list.add(new Entry("Gazilov", 89));
            list.add(new Entry("Gazilov", 55));
            list.add(new Entry("Bilyk", 8));
            list.add(new Entry("Badko", 80));
            list.add(new Entry("Dumko", 80));
            list.add(new Entry("bGAzkp", 80));
            list.add(new Entry("GAV", 80));
            list.add(new Entry("GAB", 5));

            System.out.print("Original list: ");
            System.out.println(list.toString());


            Collections.sort(list);
            System.out.println();
            System.out.print("Sorted list: ");
            System.out.println(list.toString());


            System.out.println("KeyDirectSearch (Badko): " + list.keyDirectSearch("Badko"));

            System.out.println("Start keybinary searching...");
            System.out.println(list.keyBinarySearch("Bilyk").toString());

            System.out.println();
            System.out.println("Looking for coincidence...");
            System.out.println(list.maxCoincidence("gaz"));

            System.out.println();
        }
}