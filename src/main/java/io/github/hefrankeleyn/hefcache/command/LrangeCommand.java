package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class LrangeCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.LRANGE.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] values = getParamsNoKey(args);
        int beginIndex = Integer.parseInt(values[0]);
        int endIndex = Integer.parseInt(values[1]);
        String[] vals = cache.lrange(key, beginIndex, endIndex);
        return Reply.arrayReply(vals);
    }
}
