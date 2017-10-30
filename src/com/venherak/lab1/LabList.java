package com.venherak.lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class LabList extends ArrayList<Entry> {
    private static int queue;
    private static final String filepath = "/home/indeoo/Projects/SystemProg/";
    private static final String filename = "queue";
    private static final File file;
    static {
        file = new File(filepath + filename);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        readFile();
        System.out.println("Current queue: " + queue + "\n");
    }


    Entry maxCoincidence(String key) {
        char[] keyChars = key.toLowerCase().toCharArray();
        int edge = keyChars.length;
        int counter;
        int[] counterArr = new int[this.size()];
        List<Entry> list = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            char[] listChars = this.get(i).getKey().toLowerCase().toCharArray();
            counter = 0;
            for (int j = 0; j < edge; j++) {
                if (keyChars[j] == listChars[j]) {
                    counter++;
                } else {
                    break;
                }
                counterArr[i] = counter;
            }

        }
        int maxElement = findMaxElement(counterArr);
        boolean positionOfMaxElementArr[] = searchElementInArray(maxElement, counterArr);
        for (int i = 0; i < this.size(); i++) {
            if (positionOfMaxElementArr[i]) {
                list.add(this.get(i));
            }
        }
        Entry entry = list.get(queue);
        queue++;
        if (queue == list.size()) {
            queue = 0;
        }

        writeFile(queue);

        return entry;
    }

    private int findMaxElement(int[] entryArr) {
        int max = Integer.MIN_VALUE;

        for (int anEntryArr : entryArr) {
            if (max < anEntryArr) {
                max = anEntryArr;
            }
        }
        return max;
    }

    private boolean[] searchElementInArray(int key, int[] arr) {
        boolean[] resultArr = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++) {
            resultArr[i] = (key == arr[i]);
        }
        return resultArr;
    }

    Entry keyDirectSearch(String key) throws NoSuchElementException {
        for (Entry entry : this) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

    Entry keyBinarySearch(String key) throws NoSuchElementException {
        int compare;
        int left = 0;
        int right = this.size() - 1;
        int result = left;

        while ((compare = this.get(result).getKey().compareTo(key)) != 0) {
            result = (left + right) / 2;
            if (compare < 0) {
                left++;
            } else {
                right--;
            }
            if (left > right) {
                throw new NoSuchElementException();
            }
        }
        return this.get(result);
    }

    private static void writeFile(int queue) {
        OutputStream fis = null;
        try {
            fis = new FileOutputStream(file);
            try {
                fis.write((byte) Integer.toString(queue).toCharArray()[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fis != null;
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readFile() {
        InputStream fis = null;
        Character symbol = 0;
        try {
            fis = new FileInputStream(file);
            try {
                symbol = (char) fis.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            queue = Integer.parseInt(symbol.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            queue = 0;
                writeFile(queue);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}