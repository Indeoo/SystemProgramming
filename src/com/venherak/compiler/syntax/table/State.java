package com.venherak.compiler.syntax.table;

import com.venherak.compiler.languages.Language;
import com.venherak.compiler.syntax.Rule;
import com.venherak.compiler.syntax.alphabet.AbstractSymbol;
import com.venherak.compiler.syntax.alphabet.NonTerminal;

import java.util.ArrayList;
import java.util.List;

public class State {
    private List<Item> itemList;
    private Language language;
    private boolean accept = false;
    private List<State> nextStates;

    public State(Language language) {
        this.language = language;
        itemList = new ArrayList<>();
        Item item = new Item(language.findRulesBegin(new NonTerminal("Statements")).get(0));
        itemList.add(item);
        closure(item);
        nextStates = new ArrayList<>();
    }

    public State(List<Item> itemList, Language language) {
        this.language = language;
        this.itemList = new ArrayList<>();
        this.itemList.addAll(itemList);
        for (Item item : itemList) {
            closure(item);
        }
        nextStates = new ArrayList<>();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<AbstractSymbol> getSignals() {
        List<AbstractSymbol> abstractSymbols = new ArrayList<>();
        for (Item item : itemList) {
            abstractSymbols.add(item.getRightSymbol());
        }
        return abstractSymbols;
    }

    private void closure(Item item) {
        List<Rule> rules = language.getProductionsOf(item.getRightSymbol());
        for (Rule rule : rules) {
            Item item1 = new Item(rule);
            if (!itemList.contains(item1)) {
                itemList.add(item1);
            }
            if (language.getProductionsOf(item1.getRightSymbol()).size() > 0
                    && !item1.getRightSymbol().getLiteral().equals(item.getRightSymbol().getLiteral())) {
                closure(item1);
            }
        }
    }

    public void formStates(List<State> states) {
        State state;

        for (int i = 0; i < getSignals().size(); i++) {
            List<Item> items = new ArrayList<>();

            for (Item item : itemList) {
                System.out.println(getSignals().get(i).getLiteral() + "  " + item.getRightSymbol().getLiteral());
                if (getSignals().get(i).getLiteral().equals(item.getRightSymbol().getLiteral())) {
                    System.out.println(item);
                    System.out.println(item.getShiftedCopy());
                    System.out.println("KEKO  " + item.getRightSymbol().getLiteral());
                    items.add(item.getShiftedCopy());
                }
            }
            state = new State(items, language);
            System.out.println(state.hashCode());
            System.out.println(!states.contains(state));
            System.out.println("POINT  ");
            if (!states.contains(state)) {
                System.out.println("OSSSSS");
                System.out.println(state);
                nextStates.add(state);
                states.add(state);
                if (state.itemList.size() >= 1) {
                    state.formStates(states);
                }
            } else {
                for (State state1 : states) {
                    if (state.equals(state1)) {
                        nextStates.add(state1);
                    }
                }
            }
        }
    }

    public List<State> getNextStates() {
        return nextStates;
    }

    public State getNextBySignal(AbstractSymbol signal) {
        for (int i = 0; i < nextStates.size(); i++) {
            if (getSignals().get(i).getLiteral().equals(signal.getLiteral())) {
                return nextStates.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\n--STATE--\n");
        for (Item item : itemList) {
            result.append(item).append("\n");
        }
        result.append("---------\n");
        return result.toString();
    }

    public boolean isFinishState() {
        return itemList.get(0).isFinish();
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == hashCode();
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Item item : itemList) {
            hashCode += item.hashCode();
        }
        return hashCode / 10;
    }
}
