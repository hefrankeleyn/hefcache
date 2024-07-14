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
public class ZrankCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.ZRANK.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String value = getValue(args);
        Integer index = cache.zrank(key, value);
        return Reply.integerReply(index);
    }
}
