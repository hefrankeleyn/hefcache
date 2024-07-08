package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class InfoCommand implements Command {

    private static final String INFO_VAL = "HefCache server[0.0.1], create by hef." + CRLF
            + "Mock server at 2024-06-30 17:37 in BeiJing" + CRLF;
    @Override
    public String name() {
        return CommandEnum.INFO.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        return Reply.bulkReply(INFO_VAL);
    }
}
