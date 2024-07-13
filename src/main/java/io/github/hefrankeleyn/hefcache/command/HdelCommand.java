package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class HdelCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.HDEL.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] fields = getParamsNoKey(args);
        int num = cache.hdel(key, fields);
        return Reply.integerReply(num);
    }
}
