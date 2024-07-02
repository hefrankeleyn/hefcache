package io.github.hefrankeleyn.hefcache.core;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @Date 2024/7/2
 * @Author lifei
 */
public class HefCache {

    private Map<String, String> map = Maps.newHashMap();

    public String get(String key){
        return map.get(key);
    }

    public void set(String key, String value) {
        map.put(key, value);
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
        return Arrays.stream(keys).map(map::get).toArray(String[]::new);
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
            map.putIfAbsent(key, "0");
            int result = Integer.parseInt(map.get(key)) + 1;
            this.set(key, String.valueOf(result));
            return result;
        }catch (NumberFormatException e){
            throw e;
        }
    }

    public int decr(String key) {
        try {
            map.putIfAbsent(key, "0");
            int result = Integer.parseInt(map.get(key)) - 1;
            this.set(key, String.valueOf(result));
            return result;
        }catch (NumberFormatException e){
            throw e;
        }
    }
}
