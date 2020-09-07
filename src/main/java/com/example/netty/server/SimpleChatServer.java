package com.example.netty.server;

import com.example.netty.handler.SimpleChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleChatServer {
    private int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            System.out.println("start");
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
            System.out.println("shutdown");
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }else {
            port = 8080;
        }
        new SimpleChatServer(port).run();
    }
}
