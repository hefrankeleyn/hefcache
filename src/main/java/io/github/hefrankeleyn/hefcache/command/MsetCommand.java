package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class MsetCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.MSET.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String[] keys = getKeys(args);
        String[] vals = getValues(args);
        cache.mset(keys, vals);
        return Reply.simpleReply(OK);
    }
}
