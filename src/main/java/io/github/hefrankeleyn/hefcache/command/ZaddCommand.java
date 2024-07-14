package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

import java.util.Arrays;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class ZaddCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.ZADD.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        double[] sources = toDoubleArray(getHKeys(args));
        String[] vals = getHValues(args);
        int len = cache.zadd(key, sources, vals);
        return Reply.integerReply(len);
    }

    private double[] toDoubleArray(String[] sources) {
        return Arrays.stream(sources).mapToDouble(Double::parseDouble).toArray();
    }
}
