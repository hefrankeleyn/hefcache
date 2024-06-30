package io.github.hefrankeleyn.hefcache;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date 2024/6/30
 * @Author lifei
 */
public class HefCacheDecoder extends ByteToMessageDecoder {


    private AtomicLong decodeCount = new AtomicLong(0);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("===> HefCacheDecoder decodeCount: " + decodeCount.incrementAndGet());
        if (byteBuf.readableBytes() <=0 ) {
            return;
        }
        int len = byteBuf.readableBytes();
        int index = byteBuf.readerIndex();
        System.out.println("===> readable num: " + len + " index: " + index);
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        String res = new String(bytes);
        System.out.println("===> res: " + res);
        list.add(res);
    }
}
