package cn.blingfeng.netty.sana.client.handler;

import cn.blingfeng.netty.sana.codec.Byte2MessageCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class SanaClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new Byte2MessageCodec())
                .addLast(new SanaClientHandler());
    }
}
