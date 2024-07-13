package io.github.hefrankeleyn.hefcache.core;

/**
 * @Date 2024/7/5
 * @Author lifei
 */
public enum CommandEnum {
    COMMAND,
    PING,
    INFO,
    SET,
    GET,
    STRLEN,
    EXISTS,
    DEL,
    MSET,
    MGET,
    INCR,
    DECR,
    UNKNOWN,
    // LIST
    LPUSH,
    RPUSH,
    LPOP,
    RPOP,
    LLEN,
    LINDEX,
    LRANGE,
    // SET
    SADD,
    SMEMBERS,
    SCARD,
    SISMEMBER,
    SREM,
    SPOP
    // hash

    // zset
    ;
}
