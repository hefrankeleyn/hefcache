package io.github.hefrankeleyn.hefcache.core;

import com.google.common.base.MoreObjects;

public class Reply<T> {

    T value;
    ReplyTypeEnum replyType;

    public Reply() {
    }

    public Reply(T value, ReplyTypeEnum replyType) {
        this.value = value;
        this.replyType = replyType;
    }

    public static Reply<String> simpleReply(String value) {
        return new Reply<>(value, ReplyTypeEnum.SIMPLE_STRING);
    }

    public static Reply<String> bulkReply(String value) {
        return new Reply<>(value, ReplyTypeEnum.BULK_STRING);
    }

    public static Reply<String[]> arrayReply(String[] values) {
        return new Reply<>(values, ReplyTypeEnum.ARRAY);
    }


    public static Reply<String> errorReply(String value) {
        return new Reply<>(value, ReplyTypeEnum.ERROR);
    }

    public static Reply<Integer> integerReply(Integer value) {
        return new Reply<>(value, ReplyTypeEnum.INTEGER);
    }

    public T getValue() {
        return value;
    }

    public ReplyTypeEnum getReplyType() {
        return replyType;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setReplyType(ReplyTypeEnum replyType) {
        this.replyType = replyType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Reply.class)
                .add("value", value)
                .add("replyType", replyType)
                .toString();
    }
}
