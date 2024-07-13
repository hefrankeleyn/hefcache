package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class SismemberCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.SISMEMBER.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String value = getValue(args);
        int res = cache.sismember(key, value);
        return Reply.integerReply(res);
    }
}
