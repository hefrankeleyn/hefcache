package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

import java.util.Objects;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class LlenCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.LLEN.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        int len = cache.llen(key);
        return Reply.integerReply(len);
    }
}
