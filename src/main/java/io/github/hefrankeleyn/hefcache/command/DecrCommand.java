package io.github.hefrankeleyn.hefcache.command;

import com.google.common.base.Strings;
import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class DecrCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.DECR.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        try {
            int val = cache.decr(key);
            return Reply.integerReply(val);
        }catch (NumberFormatException e) {
            String err = Strings.lenientFormat("NFE key: %s , value is not integer.", key);
            return Reply.errorReply(err);
        }
    }
}
