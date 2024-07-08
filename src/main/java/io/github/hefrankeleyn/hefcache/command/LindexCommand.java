package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class LindexCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.LINDEX.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        int index = Integer.parseInt(getValue(args));
        String val = cache.lindex(key, index);
        return Reply.bulkReply(val);
    }
}
