package io.github.hefrankeleyn.hefcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.security.cert.CRL;

/**
 * @Date 2024/6/30
 * @Author lifei
 */
public class HefCacheHandler extends SimpleChannelInboundHandler<String> {

    private static final String CRLF = "\r\n";
    private static final String STR_PREFIX_SIMPLE = "+";
    private static final String STR_PREFIX_BULK = "$";
    private static final String OK = "OK";
    private static final String COMMAND = "COMMAND";
    private static final String PING = "PING";
    private static final String INFO = "INFO";
    private static final String INFO_VAL = "HefCache server[0.0.1], create by hef." + CRLF
                                            + "Mock server at 2024-06-30 17:37 in BeiJing" + CRLF;;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                String message) throws Exception {
        // 将message切分为数组
        String[] args = message.split(CRLF);
        System.out.println("HefCacheHandler ==> " + String.join(",", args));
        channelHandlerContext.writeAndFlush("+OK\r\n");
        //
        String cmd = args[2].toUpperCase();
        if (cmd.equals(COMMAND)) {
            // *len$len
            String result = "*" + 2 + CRLF + "$" + COMMAND.length() + CRLF + COMMAND + CRLF + "$" + PING.length() + CRLF + PING + CRLF;
            writeByteBuffer(channelHandlerContext, result);
        } else if (cmd.equals(PING)) {
            String result = "PONG";
            // *2\r\n$4\r\nPING\r\n$n\r\nxxx\r\n
            if (args.length >= 5) {
                result = args[4];
            }
            writeSimpleString(channelHandlerContext, result);
        } else if (cmd.equals(INFO)) {
            writeBulkString(channelHandlerContext, INFO_VAL);
        } else {
            writeSimpleString(channelHandlerContext, OK);
        }
    }

    private void writeBulkString(ChannelHandlerContext channelHandlerContext, String context) {
        writeByteBuffer(channelHandlerContext, STR_PREFIX_BULK + context.getBytes().length + CRLF + context + CRLF);
    }

    private void writeSimpleString(ChannelHandlerContext channelHandlerContext, String context) {
        writeByteBuffer(channelHandlerContext, STR_PREFIX_SIMPLE + context + CRLF);
    }

    private void writeByteBuffer(ChannelHandlerContext channelHandlerContext, String context) {
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(context.getBytes());
        channelHandlerContext.writeAndFlush(buffer);
    }
}
