package io.github.hefrankeleyn.hefcache.core;

import com.google.common.base.Strings;
import io.github.hefrankeleyn.hefcache.command.UnknownCommand;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Date 2024/6/30
 * @Author lifei
 */
public class HefCacheHandler extends SimpleChannelInboundHandler<String> {

    private static final String CRLF = "\r\n";
    private static final String STR_PREFIX_SIMPLE = "+";
    private static final String STR_PREFIX_BULK = "$";
    private static final String STR_PREFIX_NUMBER = ":";
    private static final String STR_PREFIX_ARRAY = "*";
    private static final String STR_PREFIX_ERROR = "-";
    private static final String OK = "OK";
    private static final String COMMAND = "COMMAND";
    private static final String PING = "PING";
    private static final String INFO = "INFO";
    private static final String SET = "SET";
    private static final String GET = "GET";
    private static final String STRLEN = "STRLEN";
    private static final String EXISTS = "EXISTS";
    private static final String DEL = "DEL";
    private static final String MSET = "MSET";
    private static final String MGET = "MGET";
    private static final String INCR = "INCR";
    private static final String DECR = "DECR";
    private static final String INFO_VAL = "HefCache server[0.0.1], create by hef." + CRLF
                                            + "Mock server at 2024-06-30 17:37 in BeiJing" + CRLF;

    private static final HefCache cache = new HefCache();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                String message) throws Exception {

        System.out.println(Strings.lenientFormat("HefCacheHandler ==> message: %s" , message));
        // 将message切分为数组
        String[] args = message.split(CRLF);
        // 获取命令
        String cmd = args[2].toUpperCase();
        Command command = Commands.getCommand(cmd);
        Reply<?> reply = command.exec(cache, args);
        System.out.println(Strings.lenientFormat("====> COM: %s, reply: %s", cmd, reply));
        writeReply(channelHandlerContext, reply);
    }

    private void writeReply(ChannelHandlerContext channelHandlerContext, Reply<?> reply) {
        if (Objects.equals(reply.getReplyType(), ReplyTypeEnum.SIMPLE_STRING)) {
            writeSimpleString(channelHandlerContext, (String) reply.getValue());
        } else if (Objects.equals(reply.getReplyType(), ReplyTypeEnum.BULK_STRING)) {
            writeBulkString(channelHandlerContext, (String) reply.getValue());
        } else if (Objects.equals(reply.getReplyType(), ReplyTypeEnum.INTEGER)) {
            writeInteger(channelHandlerContext, (Integer) reply.getValue());
        } else if (Objects.equals(reply.getReplyType(), ReplyTypeEnum.ARRAY)) {
            writeArray(channelHandlerContext, (String[]) reply.getValue());
        } else if (Objects.equals(reply.getReplyType(), ReplyTypeEnum.ERROR)) {
            writeError(channelHandlerContext, (String) reply.getValue());
        } else {
            writeSimpleString(channelHandlerContext, OK);
        }
    }

    private String errorString(String message) {
        return STR_PREFIX_ERROR + message + CRLF;
    }

    private void writeError(ChannelHandlerContext channelHandlerContext, String message) {
        writeByteBuffer(channelHandlerContext, errorString(message));
    }

    private String arrayString(Object[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append(STR_PREFIX_ARRAY);
        if (Objects.isNull(array)) {
            sb.append("-1");
            sb.append(CRLF);
        } else if (array.length==0) {
            sb.append("0");
            sb.append(CRLF);
        }else {
            sb.append(array.length);
            sb.append(CRLF);
            for (Object val : array) {
                if (Objects.isNull(val)) {
                    sb.append(STR_PREFIX_BULK + "-1" + CRLF);
                }else if (val instanceof Integer numVal) {
                    sb.append(integerString(numVal));
                }else if (val instanceof String stringVal) {
                    sb.append(bulkString(stringVal));
                }else if (val instanceof Object[] subArray) {
                    arrayString(subArray);
                } else {
                    sb.append(bulkString(val));
                }
            }
        }
        return sb.toString();
    }

    private void writeArray(ChannelHandlerContext channelHandlerContext, String[] vals) {
        writeByteBuffer(channelHandlerContext, arrayString(vals));
    }

    private String integerString(int num) {
        return STR_PREFIX_NUMBER + num + CRLF;
    }

    private void writeInteger(ChannelHandlerContext channelHandlerContext, int num) {
        writeByteBuffer(channelHandlerContext, integerString(num));
    }

    private String bulkString(Object context) {
        String result;
        if (Objects.isNull(context)) {
            result = STR_PREFIX_BULK + "-1" + CRLF;
        } else if (context.toString().isEmpty()) {
            result = STR_PREFIX_BULK + "0" + CRLF;
        } else {
            result = STR_PREFIX_BULK + context.toString().getBytes().length + CRLF + context + CRLF;
        }
        return result;
    }

    private void writeBulkString(ChannelHandlerContext channelHandlerContext, String context) {
        writeByteBuffer(channelHandlerContext, bulkString(context));
    }

    private String simpleString(String context) {
        String result;
        if (Objects.isNull(context)) {
            result = STR_PREFIX_BULK + "-1" + CRLF;
        } else if (context.isEmpty()) {
            result = STR_PREFIX_BULK + "0" + CRLF;
        }else {
            result = STR_PREFIX_SIMPLE + context + CRLF;
        }
        return result;
    }

    private void writeSimpleString(ChannelHandlerContext channelHandlerContext, String context) {
        writeByteBuffer(channelHandlerContext, simpleString(context));
    }

    private void writeByteBuffer(ChannelHandlerContext channelHandlerContext, String context) {
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(context.getBytes());
        channelHandlerContext.writeAndFlush(buffer);
    }
}
