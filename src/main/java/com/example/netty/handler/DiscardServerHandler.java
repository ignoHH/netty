package com.example.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 处理服务端
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        /*ByteBuf in = (ByteBuf)msg;
        try {
            *//*while (in.isReadable()) {
                System.out.print((char)in.readByte());
                System.out.flush();
            }*//*
            *//*System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));*//*
        } finally {
            ReferenceCountUtil.release(msg);
        }*/

        // write(Object) 方法来逐字地把接受到的消息写入。
        // 请注意不同于 DISCARD 的例子我们并没有释放接受到的消息，
        // 这是因为当写入的时候 Netty 已经帮我们释放了
        ctx.write(msg);
        // ctx.write(Object) 方法不会使消息写入到通道上，他被缓冲在了内部，
        // 你需要调用 ctx.flush() 方法来把缓冲区中数据强行输出。
        // 或者你可以用更简洁的 cxt.writeAndFlush(msg) 以达到同样的目的
        ctx.flush();

    }
    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ((ByteBuf) msg).release();
    }*/

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
