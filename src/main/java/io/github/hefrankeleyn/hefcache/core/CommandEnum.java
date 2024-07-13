package io.github.hefrankeleyn.hefcache.core;

/**
 * @Date 2024/7/5
 * @Author lifei
 */
public enum CommandEnum {
    COMMAND,
    PING,
    INFO,
    UNKNOWN,
    // String
    SET,
    GET,
    STRLEN,
    EXISTS,
    DEL,
    MSET,
    MGET,
    INCR,
    DECR,
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
    SPOP,
    // hash
    HSET,
    HGET,
    HGETALL,
    HMGET,
    HLEN,
    HDEL,
    HEXISTS

    // zset
    ;
}
