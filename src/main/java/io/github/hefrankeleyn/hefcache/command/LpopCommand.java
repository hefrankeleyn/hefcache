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
public class LpopCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.LPOP.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        try {
            String key = getKey(args);
            int num = 1;
            if (args.length >= 7) {
                num = Integer.parseInt(getValue(args));
                String[] values = cache.lpop(key, num);
                return Reply.arrayReply(values);
            } else {
                String[] values = cache.lpop(key, num);
                String result = Objects.nonNull(values) && values.length>0 ? values[0] : null;
                return Reply.bulkReply(result);
            }
        }catch (Exception e) {
            return Reply.errorReply(e.getMessage());
        }
    }
}
