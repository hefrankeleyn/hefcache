package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class SmembersCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.SMEMBERS.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] result = cache.smembers(key);
        return Reply.arrayReply(result);
    }
}
