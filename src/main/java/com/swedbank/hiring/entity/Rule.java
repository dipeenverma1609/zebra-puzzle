package com.swedbank.hiring.entity;

public class Rule {

    private HousePositionReference direction;
    private Pair<String, String> pair1;
    private Pair<String, String> pair2;

    public Rule(HousePositionReference direction, Pair<String, String> pair1, Pair<String, String> pair2) {
        this.direction = direction;
        this.pair1 = pair1;
        this.pair2 = pair2;
    }

    public HousePositionReference getDirection() {
        return direction;
    }

    public boolean isSame() {
        return getDirection().equals(HousePositionReference.SAME);
    }

    public Pair<String, String> getPair1() {
        return pair1;
    }

    public Pair<String, String> getPair2() {
        return pair2;
    }

    public boolean containsKey(String key) {
        if (key != null) {
            return (pair1 != null && getPair1().getKey().equals(key))
                || (pair2 != null && getPair2().getKey().equals(key));
        }
        return false;
    }
}