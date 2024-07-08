package io.github.hefrankeleyn.hefcache.command;

import com.google.common.base.Strings;
import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

/**
 * @Date 2024/7/8
 * @Author lifei
 */
public class UnknownCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.UNKNOWN.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String com = getCom(args);
        String message = Strings.lenientFormat("Unknown command: %s", com);
        return Reply.errorReply(message);
    }
}
