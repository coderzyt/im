package com.clay.im.access.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.StandardCharsets;

/**
 * @author clay
 */
public class AccessServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        ByteBuf byteBuf = (ByteBuf) message;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        System.out.println("accept message: " + body);
        ByteBuf resp = Unpooled.copiedBuffer("success".getBytes());
        ctx.write(resp);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            // 心跳处理
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        e.printStackTrace();
    }
}
