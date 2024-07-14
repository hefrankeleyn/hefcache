package io.github.hefrankeleyn.hefcache.core;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * @Date 2024/7/13
 * @Author lifei
 */
public class ZsetEntry implements Comparable<ZsetEntry> {

    private double score;
    private String value;

    public ZsetEntry() {}

    public ZsetEntry(double score, String value) {
        this.score = score;
        this.value = value;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZsetEntry zsetEntry)) return false;
        return Objects.equals(getValue(), zsetEntry.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ZsetEntry.class)
                .add("score", score)
                .add("value", value)
                .toString();
    }

    @Override
    public int compareTo(ZsetEntry o) {
        if (this.equals(o)) {
            return 0;
        }
        int com = Double.compare(this.score, o.getScore());
        if (com!=0) {
            return com;
        }
        return value.compareTo(o.getValue());
    }
}
