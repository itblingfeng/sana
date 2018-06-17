package cn.blingfeng.netty.sana.codec;

import cn.blingfeng.netty.sana.protocol.SanaResponse;
import cn.blingfeng.netty.sana.util.ExchangetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;


import static org.junit.Assert.*;

public class Byte2MessageCodecTest {
    @Test
    public void testEncode() {
        SanaResponse response = ExchangetUtil.successRes("server send data to client");
        EmbeddedChannel channel = new EmbeddedChannel(new Byte2MessageCodec());
        assertTrue(channel.writeOutbound(response));
        assertTrue(channel.finish());
        ByteBuf out = channel.readOutbound();
        assertEquals(CodecConstant.MESSAGE_TYPE_RES, out.readByte());
        System.out.println("length: " + out.readInt());
        channel.close();
    }

    @Test
    public void testDecode() {
        SanaResponse response = ExchangetUtil.successRes("server send data to client");
        EmbeddedChannel channel = new EmbeddedChannel(new Byte2MessageCodec());
        assertTrue(channel.writeOutbound(response));
        assertTrue(channel.finish());
        ByteBuf out = channel.readOutbound();
        EmbeddedChannel channel1 = new EmbeddedChannel(new Byte2MessageCodec());
        assertTrue(channel1.writeInbound(out));
        assertTrue(channel1.finish());
        SanaResponse sanaResponse = channel1.readInbound();
        assertNotNull(sanaResponse);
        System.out.println(sanaResponse.toString());
    }
}