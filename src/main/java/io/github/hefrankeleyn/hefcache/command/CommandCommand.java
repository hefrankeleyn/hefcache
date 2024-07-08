package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.*;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class CommandCommand implements Command {
    @Override
    public String name() {
        return CommandEnum.COMMAND.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        return Reply.arrayReply(Commands.getCommandNames());
    }
}
