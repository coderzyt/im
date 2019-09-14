package com.clay.im.access.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

/**
 * @author clay
 */
public class AccessLogicConnector {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1024, 1024, 1024))
                    .handler(new ConnectorChannelHandler());

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ConnectorChannelHandler extends ChannelInitializer<NioSocketChannel> {

        @Override
        protected void initChannel(NioSocketChannel ch) throws Exception {
            // 添加自定义编解码器
            ch.pipeline().addLast(new HttpClientCodec());
            ch.pipeline().addLast(new ConnectorChannelHandler());
        }
    }
}
