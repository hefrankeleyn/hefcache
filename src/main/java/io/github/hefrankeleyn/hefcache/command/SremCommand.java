package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class SremCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.SREM.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getParamsNoKey(args);
        int result = cache.srem(key, values);
        return Reply.integerReply(result);
    }
}
