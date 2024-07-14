package io.github.hefrankeleyn.hefcache.command;

import io.github.hefrankeleyn.hefcache.core.Command;
import io.github.hefrankeleyn.hefcache.core.CommandEnum;
import io.github.hefrankeleyn.hefcache.core.HefCache;
import io.github.hefrankeleyn.hefcache.core.Reply;

import javax.crypto.spec.PSource;
import java.util.Objects;


/**
 * @Date 2024/7/5
 * @Author lifei
 */
public class ZscoreCommand implements Command {

    @Override
    public String name() {
        return CommandEnum.ZSCORE.name();
    }

    @Override
    public Reply<?> exec(HefCache cache, String[] args) {
        String key = getKey(args);
        String value = getValue(args);
        Double score = cache.zscore(key, value);
        String result = Objects.isNull(score)? null: String.valueOf(score);
        return Reply.bulkReply(result);
    }
}
