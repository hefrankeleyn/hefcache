package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class SaddCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.SADD.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getParamsNoKey(args);
        int len = cache.sadd(key, values);
        return Reply.integerReply(len);
    }
}
