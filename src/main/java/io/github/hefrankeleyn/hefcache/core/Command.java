package io.github.hefrankeleyn.hefcache.core;

import java.util.stream.Stream;

public interface Command {
    String CRLF = "\r\n";
    String OK = "OK";
    String name();
    Reply<?> exec(HefCache cache, String[] args);

    default String getCom(String[] args) {
        return args[2].toUpperCase();
    }

    default String getKey(String[] args) {
        return args[4];
    }

    default String[] getParameters(String[] args) {
        return Stream.iterate(4, index->index<args.length, index->index+2).map(index->args[index]).toArray(String[]::new);
    }

    default String getValue(String[] args) {
        return args[6];
    }

    default String[] getKeys(String[] args) {
        return Stream.iterate(4, index->index<args.length, index->index+4).map(index->args[index]).toArray(String[]::new);
    }

    default String[] getValues(String[] args) {
        return Stream.iterate(6, index->index<args.length, index->index+4).map(index->args[index]).toArray(String[]::new);
    }

    default String[] getParamsNoKey(String[] args) {
        return Stream.iterate(6, index->index<args.length, index->index+2).map(index->args[index]).toArray(String[]::new);
    }

    default String[] getHKeys(String[] args) {
        return Stream.iterate(6, index->index<args.length, index->index+4).map(index->args[index]).toArray(String[]::new);
    }

    default String[] getHValues(String[] args) {
        return Stream.iterate(8, index->index<args.length, index->index+4).map(index->args[index]).toArray(String[]::new);
    }
}
