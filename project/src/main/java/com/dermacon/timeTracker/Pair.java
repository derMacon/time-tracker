package com.dermacon.timeTracker;

public class Pair<E, T> {

    private final E fst;
    private final T snd;

    public Pair(E fst, T snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public E getFst() {
        return fst;
    }

    public T getSnd() {
        return snd;
    }
}
