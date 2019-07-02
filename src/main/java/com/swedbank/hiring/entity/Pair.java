package com.swedbank.hiring.entity;

public class Pair<A,B> {

    private A key;
    private B value;

    public Pair(A key, B value) {
        this.key = key;
        this.value = value;
    }

    public A getKey() {
        return key;
    }

    public B getValue() {
        return value;
    }

    public boolean contains(A key){
        return key != null && key.equals(getKey());
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
