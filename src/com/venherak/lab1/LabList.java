package com.venherak.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class LabList extends ArrayList<Entry> {
    public static int queue = 0;

    public Entry maxCoincidence(String key) {
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
        for (int i = 0; i < this.size() ; i++) {
            if (positionOfMaxElementArr[i]) {
                list.add(this.get(i));
            }
        }
        Entry entry = list.get(queue);
        if (list.size() > 1) {
            queue++;
        }
        return entry;
    }

    public int findMaxElement(int[] entryArr) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < entryArr.length; i++) {
            if (max < entryArr[i]) {
                max = entryArr[i];
            }
        }
        return max;
    }

    public boolean[] searchElementInArray(int key, int[] arr) {
        boolean[] resultArr = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++) {
            resultArr[i] = (key == arr[i]);
        }
        return resultArr;
    }

    public Entry keyDirectSearch(String key) throws NoSuchElementException {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getKey().equals(key)) {
                return this.get(i);
            }
        }
        return null;
    }

    public Entry keyBinarySearch(String key) throws NoSuchElementException {
        int compare;
        int left = 0;
        int right = this.size()-1;
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
}