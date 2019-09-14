package com.clay.im.access;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author clay
 */
public class AccessServerBootstrap {

    public void bind(int port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new AccessChannelHandler());
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private static class AccessChannelHandler extends ChannelInitializer<ServerChannel> {
        @Override
        protected void initChannel(ServerChannel ch) throws Exception {
            ch.pipeline().addLast(new AccessServerHandler());
        }
    }
}
