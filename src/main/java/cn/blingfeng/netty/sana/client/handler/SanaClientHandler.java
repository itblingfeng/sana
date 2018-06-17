package cn.blingfeng.netty.sana.client.handler;


import cn.blingfeng.netty.sana.protocol.SanaResponse;
import cn.blingfeng.netty.sana.util.ExchangetUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SanaClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SanaResponse response = (SanaResponse) msg;
        System.out.println(response.toString());
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // consider use message2Messagecodec
        ctx.writeAndFlush(ExchangetUtil.successReq("-> Hello Server = v = "));
        ctx.fireChannelActive();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.channel().eventLoop().shutdownGracefully();
    }
}
