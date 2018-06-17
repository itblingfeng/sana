package cn.blingfeng.netty.sana.codec;

import cn.blingfeng.netty.sana.protocol.SanaResponse;
import cn.blingfeng.netty.sana.util.ExchangetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class Byte2MessageDecoderTest {
    @Test
    public void testDecode() throws IOException {
        SanaResponse response = ExchangetUtil.successRes("server send data to client");
        EmbeddedChannel channel = new EmbeddedChannel(new Message2ByteEncoder());
        assertTrue(channel.writeOutbound(response));
        assertTrue(channel.finish());
        ByteBuf out = channel.readOutbound();
        channel.close();
        EmbeddedChannel channel1 = new EmbeddedChannel(new Byte2MessageDecoder());
        assertTrue(channel1.writeInbound(out));
        assertTrue(channel1.finish());
        SanaResponse sanaResponse = channel1.readInbound();
        assertNotNull(sanaResponse);
        System.out.println(sanaResponse.toString());
    }
}