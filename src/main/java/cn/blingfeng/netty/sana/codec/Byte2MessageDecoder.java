package cn.blingfeng.netty.sana.codec;

import cn.blingfeng.netty.sana.serialize.Serialize;
import cn.blingfeng.netty.sana.serialize.SerializeUtil;
import cn.blingfeng.netty.sana.serialize.protostuff.ProtostuffSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static cn.blingfeng.netty.sana.codec.CodecConstant.*;

public class Byte2MessageDecoder extends ByteToMessageDecoder {

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
