package com.venherak.lab1;

class Entry implements Comparable<Entry> {
    private String key;
    private int value;

    public String getKey() {
        return key;
    }

    Entry(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + key + ": " + value + "]";
    }
    @Override
    public int compareTo(Entry entry) {
        int result;
        result = this.key.compareTo(entry.key);
        if (result == 0) {
            result =  this.value - entry.value;
            return result;
        } else {
            return result;
        }
    }
}
