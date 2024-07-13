package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

import java.util.Objects;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class SpopCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.SPOP.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        int num = 1;
        if (args.length >= 7) {
            num = Integer.parseInt(getValue(args));
            String[] result = cache.spop(key, num);
            return Reply.arrayReply(result);
        } else {
            String[] arr = cache.spop(key, num);
            String result = (Objects.nonNull(arr) && arr.length>0) ? arr[0]: null;
            return Reply.bulkReply(result);
        }
    }
}
