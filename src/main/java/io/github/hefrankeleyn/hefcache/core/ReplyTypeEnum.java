package io.github.hefrankeleyn.hefcache.core;

public enum ReplyTypeEnum {
    SIMPLE_STRING("+"),
    BULK_STRING("$"),
    INTEGER(":"),
    ARRAY("*"),
    ERROR("-")
    ;

    private final String prefix;

    ReplyTypeEnum(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
