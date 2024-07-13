package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class HmgetCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.HMGET.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] fields = getParamsNoKey(args);
        String[] filedAndValueArray = cache.hmget(key, fields);
        return Reply.arrayReply(filedAndValueArray);
    }
}
