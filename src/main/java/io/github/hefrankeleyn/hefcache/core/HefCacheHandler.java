package io.github.hefrankeleyn.hefcache.core;

import com.google.common.base.Strings;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;

import static io.github.hefrankeleyn.hefcache.core.Command.OK;
import static io.github.hefrankeleyn.hefcache.core.Command.CRLF;

/**
 * @Date 2024/6/30
 * @Author lifei
 */
public class HefCacheHandler extends SimpleChannelInboundHandler<String> {

    private static final HefCache cache = new HefCache();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                String message) throws Exception {

        System.out.println(Strings.lenientFormat("HefCacheHandler ==> message: %s" , message));
        // 将message切分为数组
        String[] args = message.split(CRLF);
        // 获取命令
        String cmd = null;
        try {
            cmd= args[2].toUpperCase();
            Command command = Commands.getCommand(cmd);
            Reply<?> reply = command.exec(cache, args);
            System.out.println(Strings.lenientFormat("====> COM: %s, reply: %s", cmd, reply));
            writeReply(channelHandlerContext, reply);
        }catch (Exception e) {
            String err = Strings.lenientFormat("CMD[%s], ERR exception: %s", cmd, e.getMessage());
            writeError(channelHandlerContext, err);
        }
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
        return ReplyTypeEnum.ERROR.getPrefix() + message + CRLF;
    }

    private void writeError(ChannelHandlerContext channelHandlerContext, String message) {
        writeByteBuffer(channelHandlerContext, errorString(message));
    }

    private String arrayString(Object[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append(ReplyTypeEnum.ARRAY.getPrefix());
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
                    sb.append(ReplyTypeEnum.BULK_STRING.getPrefix() + "-1" + CRLF);
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
        return ReplyTypeEnum.SIMPLE_STRING.getPrefix() + num + CRLF;
    }

    private void writeInteger(ChannelHandlerContext channelHandlerContext, int num) {
        writeByteBuffer(channelHandlerContext, integerString(num));
    }

    private String bulkString(Object context) {
        String result;
        if (Objects.isNull(context)) {
            result = ReplyTypeEnum.BULK_STRING.getPrefix() + "-1" + CRLF;
        } else if (context.toString().isEmpty()) {
            result = ReplyTypeEnum.BULK_STRING.getPrefix() + "0" + CRLF;
        } else {
            result = ReplyTypeEnum.BULK_STRING.getPrefix() + context.toString().getBytes().length + CRLF + context + CRLF;
        }
        return result;
    }

    private void writeBulkString(ChannelHandlerContext channelHandlerContext, String context) {
        writeByteBuffer(channelHandlerContext, bulkString(context));
    }

    private String simpleString(String context) {
        String result;
        if (Objects.isNull(context)) {
            result = ReplyTypeEnum.BULK_STRING.getPrefix() + "-1" + CRLF;
        } else if (context.isEmpty()) {
            result = ReplyTypeEnum.BULK_STRING.getPrefix() + "0" + CRLF;
        }else {
            result = ReplyTypeEnum.SIMPLE_STRING.getPrefix() + context + CRLF;
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
