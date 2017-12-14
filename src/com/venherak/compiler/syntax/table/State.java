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
        for(Item item: itemList)  {
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
            if(!itemList.contains(item1)) {
               // System.out.println(itemList);
               // System.out.println("SDASDASDASDASDD           asdas");
                itemList.add(item1);
            }
            if (language.getProductionsOf(item1.getRightSymbol()).size() > 0
                    && !item1.getRightSymbol().getLiteral().equals(item.getRightSymbol().getLiteral())) {
                // System.out.println(language.getProductionsOf(item1.getRightSymbol()));
                closure(item1);
            }
        }
    }

    public void formStates(List<State> states) {
        State state = null;
        System.out.println();

        for (int i = 0; i < getSignals().size(); i++) {
            List<Item> items = new ArrayList<>();

/*            if(getSignals().get(i).getLiteral().equals("end")) {
                System.out.println(this);
                System.out.println(getSignals().get(i) + " SIGNAL");
                System.out.println();
            }*/
            for(Item item: itemList) {
                if(getSignals().get(i).getLiteral().equals(item.getRightSymbol().getLiteral())) {
                    items.add(item.getShiftedCopy());
                    //System.out.println(item.getShiftedCopy() + " KEK");
                }
            }
            state = new State(items, language);
            //state = new State(itemList.get(i).getShiftedCopy(), language);

            if (!states.contains(state)) {
                if(getSignals().get(i).getLiteral().equals("end")) {
                    System.out.println(itemList);
                    System.out.println(this);
                }
                nextStates.add(state);
                states.add(state);
                if (state.itemList.size() >= 1) {
                    state.formStates(states);
                }
            } else {
                for(State state1: states) {
                    if(state.equals(state1)) {
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
            for(int i = 0; i < nextStates.size(); i++) {
                if(getSignals().get(i).getLiteral().equals(signal.getLiteral())) {
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
