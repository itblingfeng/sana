package cn.blingfeng.netty.sana.server.handler;

import cn.blingfeng.netty.sana.protocol.SanaRequest;
import cn.blingfeng.netty.sana.util.ExchangetUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SanaServerHandler extends ChannelInboundHandlerAdapter {
    // consider use message2Messagecodec
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SanaRequest request = (SanaRequest) msg;
        System.out.println(request.toString());
        ctx.write(ExchangetUtil.successRes("-> Hello Client, There is Sana"));
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
