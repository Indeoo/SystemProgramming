package com.venherak.lab2.automate;

public class State {
    private int id;
    private State dlm;
    private State ltr;

    State(int id, State dlm, State ltr) {
        this.id = id;
    }

    public void setDlm(State dlm) {
        this.dlm = dlm;
    }

    State(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "State: " + id + " DLM: " + dlm.getId() + " LTR: " + ltr.getId();
    }

    public int getId() {
        return id;
    }

    public State getDlm() {
        return dlm;
    }

    public State getLtr() {
        return ltr;
    }

    public void setLtr(State ltr) {
        this.ltr = ltr;
    }
}
