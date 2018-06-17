package cn.blingfeng.netty.sana.client;

import cn.blingfeng.netty.sana.client.handler.SanaClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ClientLoader {
    private int port;

    ClientLoader(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 28080;
        new ClientLoader(port).start();
    }

    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(port))
                .handler(new SanaClientChannelInitializer());
        ChannelFuture f = bootstrap.connect();
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                    System.out.println("-> SANA CLIENT IS STARTED -v-");
                } else {
                    System.out.println("-> SANA CLIENT IS START FAILED");
                    group.shutdownGracefully();
                }
            }
        });
    }
}

