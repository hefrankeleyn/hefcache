package io.github.hefrankeleyn.hefcache.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * @Date 2024/7/2
 * @Author lifei
 */
public class HefCache {

    private Map<String, CacheEntry<?>> map = Maps.newHashMap();

    // ======== 1. string begin ==============

    public String get(String key){
        CacheEntry<String> entry = (CacheEntry<String>) map.get(key);
        return Objects.isNull(entry)? null: entry.getValue();
    }

    public void set(String key, String value) {
        map.put(key, new CacheEntry<>(value));
    }

    public int exists(String... keys) {
        if (Objects.isNull(keys)) {
            return 0;
        }
        return (int)Arrays.stream(keys).filter(map::containsKey).count();
    }

    public int del(String... keys) {
        if (Objects.isNull(keys)) {
            return 0;
        }
        return (int)Arrays.stream(keys).map(map::remove).filter(Objects::nonNull).count();
    }

    public String[] mget(String... keys) {
        if (Objects.isNull(keys)) {
            return null;
        }
        return Arrays.stream(keys).map(this::get).toArray(String[]::new);
    }

    public void mset(String[] keys, String[] vals) {
        if (Objects.isNull(keys) || keys.length==0) {
            return;
        }
        for (int i = 0; i < keys.length; i++) {
            this.set(keys[i], vals[i]);
        }
    }

    public int incr(String key) {
        try {
            map.putIfAbsent(key, new CacheEntry<>("0"));
            int result = Integer.parseInt(this.get(key)) + 1;
            this.set(key, String.valueOf(result));
            return result;
        }catch (NumberFormatException e){
            throw e;
        }
    }

    public int decr(String key) {
        try {
            map.putIfAbsent(key, new CacheEntry<>("0"));
            int result = Integer.parseInt(this.get(key)) - 1;
            this.set(key, String.valueOf(result));
            return result;
        }catch (NumberFormatException e){
            throw e;
        }
    }

    public int strlen(String key) {
        return Objects.isNull(this.get(key))? 0 : this.get(key).length();
    }

    // ======== 1. string end ==============
    // ======== 2. list begin ==============

    public int lpush(String key, String... values) {
        if (Objects.isNull(values)) {
            return 0;
        }
        map.putIfAbsent(key, new CacheEntry<LinkedList<String>>(Lists.newLinkedList()));
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        LinkedList<String> list = entry.getValue();
        for (String value : values) {
            list.addFirst(value);
        }
        return list.size();
    }

    public int rpush(String key, String... values) {
        if (Objects.isNull(values)) {
            return 0;
        }
        map.putIfAbsent(key, new CacheEntry<LinkedList<String>>(Lists.newLinkedList()));
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        LinkedList<String> list = entry.getValue();
        for (String value : values) {
            list.addLast(value);
        }
        return list.size();
    }

    public String[] lpop(String key, int num) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        if (Objects.isNull(entry) || entry.getValue().isEmpty()) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        int len = Math.min(num, list.size());
        String[] result = new String[len];
        for (int i = 0; i < len; i++) {
            result[i] = list.removeFirst();
        }
        return result;
    }

    public String[] rpop(String key, int num) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        if (Objects.isNull(entry) || entry.getValue().isEmpty()) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        int len = Math.min(num, list.size());
        String[] result = new String[len];
        for (int i = 0; i < len; i++) {
            result[i] = list.removeLast();
        }
        return result;
    }

    public int llen(String key) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        if (Objects.isNull(entry) || entry.getValue().isEmpty()) {
            return 0;
        }
        return entry.getValue().size();
    }

    public String lindex(String key, int index) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        if (Objects.isNull(entry) || entry.getValue().isEmpty()) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        if (index>=list.size()) {
            return null;
        }
        return list.get(index);
    }

    public String[] lrange(String key, int beginIndex, int endIndex) {
        CacheEntry<LinkedList<String>> entry = (CacheEntry<LinkedList<String>>)map.get(key);
        if (Objects.isNull(entry) || entry.getValue().isEmpty()) {
            return null;
        }
        LinkedList<String> list = entry.getValue();
        if (beginIndex>endIndex || beginIndex>=list.size()) {
            return null;
        }
        int end = Math.min(endIndex, list.size()-1);
        String[] result = new String[end-beginIndex + 1];
        for (int i = beginIndex; i<=end; i++) {
            result[i-beginIndex] = list.get(i);
        }
        return result;
    }
    // ======== 2. list end ==============
}
