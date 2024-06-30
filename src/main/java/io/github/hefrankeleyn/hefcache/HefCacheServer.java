package io.github.hefrankeleyn.hefcache;

import ch.qos.logback.classic.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Date 2024/6/30
 * @Author lifei
 */
@Component
public class HefCacheServer implements HefPlugin {

    private int port = 6379;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private Channel channel;
    @Override
    public void init() {
        // 生成 ServerChannel 的 EventLoop
        boss = new NioEventLoopGroup(1, new DefaultThreadFactory("redis-boss"));
        // 生成 Channel 的 EventLoop
        worker = new NioEventLoopGroup(16, new DefaultThreadFactory("redis - worker"));
    }

    @Override
    public void startup() {
        try {
            // 组装所有的内容
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class) // 定义serverChannel
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 为channel 的 channelPipeline 绑定 ChannelHandler
                        channel.pipeline().addLast(new HefCacheDecoder(), new HefCacheHandler());
                        }
                    });
            // 定义 channel ChannelConfig的配置
            // 设置服务器接受连接队列的长度，即等待连接的最大数量，高并发下控制等待连接的数量
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    // 开启keep-alive机制，长时间没有数据交互，仍然保持连接活跃
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 开启端口、地址重用
                    .option(ChannelOption.SO_REUSEADDR, true)
                    // TCP 接收缓冲区大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    // TCP 发送缓冲区大小
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // byteBuf 分配和优化策略
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(EpollChannelOption.SO_REUSEPORT, true);
            // 这个channel 是serverchannel
            channel = serverBootstrap.bind(port).sync().channel();
            System.out.println("==> netty server start and listener on port " + port);
            channel.closeFuture().sync();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public void shutdown() {
        if (Objects.nonNull(channel)) {
            this.channel.close();
            this.channel = null;
        }
        if (Objects.nonNull(boss)) {
            this.boss.shutdownGracefully();
            this.boss = null;
        }
        if (Objects.nonNull(worker)) {
            this.worker.shutdownGracefully();
            this.worker = null;
        }
    }
}
