package io.github.hefrankeleyn.hefcache.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Stream;

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
    // ======== 3. set begin ==============

    public int sadd(String key, String... values) {
        map.putIfAbsent(key, new CacheEntry<LinkedHashSet<String>>(new LinkedHashSet<>()));
        CacheEntry<LinkedHashSet<String>> cacheEntry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        LinkedHashSet<String> set = cacheEntry.getValue();
        if (Objects.isNull(values)) {
            return set.size();
        }
        set.addAll(Arrays.asList(values));
        return set.size();
    }

    public String[] smembers(String key) {
        CacheEntry<LinkedHashSet<String>> cacheEntry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (Objects.isNull(cacheEntry)) {
            return null;
        }
        LinkedHashSet<String> list = cacheEntry.getValue();
        return list.toArray(new String[0]);
    }

    public int scard(String key) {
        CacheEntry<LinkedHashSet<String>> cacheEntry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (Objects.isNull(cacheEntry)) {
            return 0;
        }
        LinkedHashSet<String> list = cacheEntry.getValue();
        return list.size();
    }

    public int sismember(String key, String value) {
        CacheEntry<LinkedHashSet<String>> cacheEntry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (Objects.isNull(cacheEntry)) {
            return 0;
        }
        LinkedHashSet<String> list = cacheEntry.getValue();
        return list.contains(value)? 1 : 0;
    }

    public int srem(String key, String... values) {
        CacheEntry<LinkedHashSet<String>> cacheEntry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (Objects.isNull(cacheEntry)) {
            return 0;
        }
        LinkedHashSet<String> list = cacheEntry.getValue();
        if (Objects.isNull(values)) {
            return 0;
        }
        return (int) Arrays.stream(values).filter(list::remove).count();
    }

    private static final Random random = new Random(System.currentTimeMillis());

    public String[] spop(String key, int num) {
        CacheEntry<LinkedHashSet<String>> cacheEntry = (CacheEntry<LinkedHashSet<String>>) map.get(key);
        if (Objects.isNull(cacheEntry) || cacheEntry.getValue().isEmpty()) {
            return null;
        }
        LinkedHashSet<String> set = cacheEntry.getValue();
        int len = Math.min(num, set.size());
        String[] result = new String[len];
        for (int i = 0; i < len; i++) {
            String[] array = set.toArray(String[]::new);
            String val = array[random.nextInt(len-i)];
            result[i] = val;
            set.remove(val);
        }
        return result;
    }

    // ======== 3. set end ==============
    // ======== 4. hget begin ==============

    public int hset(String key, String[] fields, String[] values) {
        map.putIfAbsent(key, new CacheEntry<HashMap<String, String>>(new HashMap<>()));
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        HashMap<String, String> hashMap = entry.getValue();
        int num = 0;
        if (Objects.isNull(fields) || fields.length == 0) {
            return num;
        }
        for (int i = 0; i < fields.length; i++) {
            if (!hashMap.containsKey(fields[i])) {
                num ++;
            }
            hashMap.put(fields[i], values[i]);
        }
        return num;
    }

    public String hget(String key, String field) {
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return null;
        }
        HashMap<String, String> hashMap = entry.getValue();
        return hashMap.get(field);
    }

    public String[] hgetall(String key) {
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return null;
        }
        HashMap<String, String> hashMap = entry.getValue();
        return hashMap.entrySet().stream()
                .flatMap(mapentry-> Stream.of(mapentry.getKey(), mapentry.getValue()))
                .toArray(String[]::new);
    }

    public String[] hmget(String key, String[] fields) {
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return null;
        }
        HashMap<String, String> hashMap = entry.getValue();
        if (Objects.isNull(fields)) {
            return null;
        }
        return Arrays.stream(fields).map(hashMap::get).toArray(String[]::new);
    }

    public int hlen(String key) {
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return 0;
        }
        HashMap<String, String> hashMap = entry.getValue();
        return hashMap.size();
    }

    public int hexists(String key, String field) {
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return 0;
        }
        HashMap<String, String> hashMap = entry.getValue();
        return hashMap.containsKey(field)? 1 : 0;
    }

    public int hdel(String key, String[] fields) {
        CacheEntry<HashMap<String, String>> entry = (CacheEntry<HashMap<String, String>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return 0;
        }
        HashMap<String, String> hashMap = entry.getValue();
        if (Objects.isNull(fields)) {
            return 0;
        }
        return (int) Arrays.stream(fields).map(hashMap::remove).filter(Objects::nonNull).count();
    }



    // ======== 4. hget end ==============
    // ======== 5. zset begin ==============
    public int zadd(String key, double[] sources, String[] values) {
        map.putIfAbsent(key, new CacheEntry<LinkedHashSet<ZsetEntry>>(new LinkedHashSet<>()));
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        LinkedHashSet<ZsetEntry> set = entry.getValue();
        int num = 0;
        for (int i = 0; i < values.length; i++) {
            ZsetEntry zsetEntry = new ZsetEntry(sources[i], values[i]);
            if (!set.remove(zsetEntry)) {
                num ++;
            }
            set.add(zsetEntry);
        }
        return num;
    }

    public int zcard(String key) {
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return 0;
        }
        LinkedHashSet<ZsetEntry> set = entry.getValue();
        return set.size();
    }

    public int zcount(String key, double minSorce, double maxSorce) {
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return 0;
        }
        LinkedHashSet<ZsetEntry> set = entry.getValue();
        return (int) set.stream().filter(item->item.getScore()>=minSorce && item.getScore()<=maxSorce).count();
    }

    public Double zscore(String key, String value) {
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return null;
        }
        LinkedHashSet<ZsetEntry> set = entry.getValue();
        Optional<ZsetEntry> optional = set.stream().filter(item -> item.getValue().equals(value)).findAny();
        if (optional.isPresent()) {
            return optional.get().getScore();
        }
        return null;
    }

    public Integer zrank(String key, String value) {
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return null;
        }
        LinkedHashSet<ZsetEntry> set = entry.getValue();
        ZsetEntry zsetEntry = new ZsetEntry(Double.MIN_VALUE, value);
        if (Objects.isNull(value) || !set.contains(zsetEntry)) {
            return null;
        }
        Double score = zscore(key, value);
        ZsetEntry currentZsetEntry = new ZsetEntry(score, value);
        return (int) set.stream().filter(item-> item.compareTo(currentZsetEntry)<0).count();
    }

    public int zrem(String key, String[] values) {
        CacheEntry<LinkedHashSet<ZsetEntry>> entry = (CacheEntry<LinkedHashSet<ZsetEntry>>) map.get(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue()) || entry.getValue().isEmpty()) {
            return 0;
        }
        if (Objects.isNull(values)) {
            return 0;
        }
        LinkedHashSet<ZsetEntry> set = entry.getValue();
        return (int) Arrays.stream(values).map(oneVal->new ZsetEntry(Double.MIN_VALUE, oneVal)).filter(set::remove).count();
    }

    // ======== 5. zset end ==============
}
