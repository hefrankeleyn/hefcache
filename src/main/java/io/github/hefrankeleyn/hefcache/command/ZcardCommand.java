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
public class ZcardCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.ZCARD.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        int len = cache.zcard(key);
        return Reply.integerReply(len);
    }
}
