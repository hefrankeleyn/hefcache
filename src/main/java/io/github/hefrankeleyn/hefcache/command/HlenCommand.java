package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class HlenCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.HLEN.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        int len = cache.hlen(key);
        return Reply.integerReply(len);
    }
}
