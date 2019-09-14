package com.clay.im.logic.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author clay
 */
public class LogicServerBootstrap {

    private int port;

    public LogicServerBootstrap(int port) {
        this.port = port;
    }

    public void bind() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1024, 1024, 1024))
                    .childHandler(new LogicChannelHandler());

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class LogicChannelHandler extends ChannelInitializer<ServerChannel> {

        @Override
        protected void initChannel(ServerChannel ch) throws Exception {
            // 添加自定义编解码器
            ch.pipeline().addLast(new HttpServerCodec());
            ch.pipeline().addLast(new IdleStateHandler(0, 0, 180));
            ch.pipeline().addLast(new LogicChannelHandler());
        }
    }
}
