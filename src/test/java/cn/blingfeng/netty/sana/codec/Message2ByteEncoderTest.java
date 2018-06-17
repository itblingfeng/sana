package cn.blingfeng.netty.sana.codec;

import cn.blingfeng.netty.sana.protocol.SanaResponse;
import cn.blingfeng.netty.sana.util.ExchangetUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class Message2ByteEncoderTest {

    @Test
    public void testEncoder() throws UnknownHostException {
        SanaResponse response = ExchangetUtil.successRes("server send data to client");
        EmbeddedChannel channel = new EmbeddedChannel(new Message2ByteEncoder());
        assertTrue(channel.writeOutbound(response));
        assertTrue(channel.finish());
        ByteBuf out = channel.readOutbound();
        assertEquals(CodecConstant.MESSAGE_TYPE_RES, out.readByte());
        System.out.println("length: " + out.readInt());
    }
}