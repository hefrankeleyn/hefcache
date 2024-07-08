package io.github.hefrankeleyn.hefcache.core;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class CacheEntry<T> {
    private T value;

    public CacheEntry() {
    }

    public CacheEntry(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
