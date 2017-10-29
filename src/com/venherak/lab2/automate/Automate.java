package com.venherak.lab2.automate;

import java.util.ArrayList;
import java.util.List;

public class Automate {
    List<State> states;
    private State current;
    private static final int DEFAULT_STATE_ID = 2;

    public Automate() {
        states = new ArrayList<>();

        for (int i = DEFAULT_STATE_ID; i < 9 + 1; i++) {
            states.add(new State(i));
        }

        for (int i = 0; i < states.size(); i++) {
            if (i + 1 == states.size()) {
                states.get(i).setDlm(states.get(i));
                states.get(i).setLtr(states.get(i));
            } else {
                states.get(i).setDlm(states.get(i + 1));
                states.get(i).setLtr(states.get(i + 1));
            }
        }

        try {
            this.getState(3).setDlm(this.getState(8));
            this.getState(5).setLtr(this.getState(3));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.current = this.getState((DEFAULT_STATE_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Automate(List<State> states, State defaultCurrent) {
        this.states = states;
        this.current = defaultCurrent;
    }

    public State getState(int id) throws Exception {
        for (State state : this.states) {
            if (state.getId() == id) {
                return state;
            }
        }
        throw new Exception("No such state");
    }

    public String toString() {
        String output = "Current state: " + current.toString() + "\n";
        for (State state : this.states) {
            output += state.toString() + "\n";
        }
        return output;
    }

    public void switchState(State state) {
        this.current = state;
    }

    public void performSignalArray(String[] signals) throws Exception {
        for (String signal : signals) {
            try {
                performSignal(signal);
            } catch (Exception e) {
                throw new Exception("There is one or more non existing signals in line");
            }
        }

    }

    public void performSignal(String signal) throws Exception {
        if (signal.equals("ltr")) {
            switchState(this.current.getLtr());
        } else {
            if (signal.equals("dlm")) {
                switchState(this.current.getDlm());
            } else {
                throw new Exception("No such signal exists");
            }
        }
    }
}
