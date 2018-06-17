package cn.blingfeng.netty.sana.codec;

import cn.blingfeng.netty.sana.protocol.SanaRequest;
import cn.blingfeng.netty.sana.serialize.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import static cn.blingfeng.netty.sana.codec.CodecConstant.MESSAGE_LENGTH;
import static cn.blingfeng.netty.sana.codec.CodecConstant.MESSAGE_TYPE_REQ;

public class Byte2MessageCodec extends ByteToMessageCodec<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        boolean isReq = false;
        if (obj instanceof SanaRequest) {
            isReq = true;
        }
        SerializeUtil.serialize(byteBuf, obj, isReq);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < CodecConstant.MESSAGE_TYPE_LENGTH) {
            return;
        }
        Boolean isReq = in.readByte() == MESSAGE_TYPE_REQ ? Boolean.TRUE : Boolean.FALSE;
        if (in.readableBytes() < MESSAGE_LENGTH) {
            return;
        }
        int len = in.readInt();
        if (in.readableBytes() < len) {
            return;
        } else {
            byte[] body = new byte[len];
            in.readBytes(body);
            out.add(SerializeUtil.deserialize(body, isReq));
        }

    }
}
