package com.star.Ba.jsf.util;

public enum Frequency {

    ANNUAL(1, "Annual"),
    SEMIANNUAL(2, "Biannual"),
    QUARTERLY(3, "Quarterly"),
    MONTHLY(4, "Monthly"),
    SINGLE(5, "Single");

    private final int code;
    private final String label;

    Frequency(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static String getLabelByCode(int code) {
        for (Frequency f : values()) {
            if (f.code == code) {
                return f.label;
            }
        }
        return null;
    }
}
