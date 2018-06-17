package cn.blingfeng.netty.sana.codec;

import cn.blingfeng.netty.sana.protocol.SanaRequest;
import cn.blingfeng.netty.sana.protocol.SanaResponse;
import cn.blingfeng.netty.sana.serialize.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Message2ByteEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        boolean isReq = false;
        if (obj instanceof SanaRequest) {
            isReq = true;
        }
        SerializeUtil.serialize(byteBuf, obj, isReq);
    }
}
