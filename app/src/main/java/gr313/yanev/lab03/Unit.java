package gr313.yanev.lab03;

import androidx.annotation.NonNull;

public class Unit {
    private final String title;
    private final double value;

    public Unit(String title, double value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
