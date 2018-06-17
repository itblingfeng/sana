package cn.blingfeng.netty.sana.server;

import cn.blingfeng.netty.sana.server.handler.SanaServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ServerLoader {
    private int port;

    public ServerLoader(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 28080;
        new ServerLoader(port).start();
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new SanaServerChannelInitializer());
        ChannelFuture f = bootstrap.bind();
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                    System.out.println("-> SANA SERVER IS STARTED -v-");
                } else {
                    System.out.println("-> SANA SERVER IS START FAILED");
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                }
            }
        });
    }
}
