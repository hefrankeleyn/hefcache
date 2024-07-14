package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class ZcountCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.ZCOUNT.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        double minSorce = Double.parseDouble(vals[0]);
        double maxSorce = Double.parseDouble(vals[1]);
        int len = cache.zcount(key, minSorce, maxSorce);
        return Reply.integerReply(len);
    }
}
