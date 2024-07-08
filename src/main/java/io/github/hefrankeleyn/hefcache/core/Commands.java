package io.github.hefrankeleyn.hefcache.core;

import com.google.common.collect.Maps;
import io.github.hefrankeleyn.hefcache.command.*;

import java.util.Map;
import java.util.Optional;

/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class Commands {

    private static final Map<String, Command> COMMANDS = Maps.newLinkedHashMap();

    static {
        initCommands();
    }

    private static void initCommands() {
        // common commands
        registerCommand(new PingCommand());
        registerCommand(new InfoCommand());
        // command
        registerCommand(new CommandCommand());
        // 未知命令
        registerCommand(new UnknownCommand());

        // string commands
        registerCommand(new SetCommand());
        registerCommand(new GetCommand());
        registerCommand(new StrlenCommand());
        registerCommand(new ExistsCommand());
        registerCommand(new DelCommand());
        registerCommand(new IncrCommand());
        registerCommand(new DecrCommand());
        registerCommand(new MsetCommand());
        registerCommand(new MgetCommand());

        // list : LPUSH RPUSH  lpop rpop lLen Lindex  Lrange
        registerCommand(new LpushCommand());
        registerCommand(new RpushCommand());
        registerCommand(new LpopCommand());
        registerCommand(new RpopCommand());
        registerCommand(new LlenCommand());
        registerCommand(new LindexCommand());
        registerCommand(new LrangeCommand());


    }

    public static void registerCommand(Command command) {
        COMMANDS.put(command.name(), command);
    }

    public static Command getCommand(String name) {
        return Optional.ofNullable(COMMANDS.get(name)).orElse(Commands.getCommand(CommandEnum.UNKNOWN));
    }

    public static Command getCommand(CommandEnum commandEnum) {
        return COMMANDS.get(commandEnum.name());
    }

    public static String[] getCommandNames() {
        return COMMANDS.keySet().toArray(new String[0]);
    }
}
