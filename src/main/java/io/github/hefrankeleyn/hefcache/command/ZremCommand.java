package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

import java.util.Objects;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class ZremCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.ZREM.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getParamsNoKey(args);
        int num = cache.zrem(key, values);
        return Reply.integerReply(num);
    }
}
