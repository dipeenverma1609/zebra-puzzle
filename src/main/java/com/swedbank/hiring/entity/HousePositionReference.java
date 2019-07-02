package com.swedbank.hiring.entity;

public enum HousePositionReference {
    SAME,
    TO_THE_LEFT_OF,
    NEXT_TO;

    public static HousePositionReference getValue(String value) {
        if (value != null && !value.isEmpty()) {
            for (HousePositionReference o : HousePositionReference.values()) {
                if (value.equals(o.name())) return o;
            }
        }
        return null;
    }
}
