package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class PingCommand implements Command {

    private static final String PONG = "PONG";
    @Override
    public String name() {
        return CommandEnum.PING.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String result = PONG;
        if (args.length >= 5) {
            result = args[4];
        }
        return Reply.simpleReply(result);
    }
}
