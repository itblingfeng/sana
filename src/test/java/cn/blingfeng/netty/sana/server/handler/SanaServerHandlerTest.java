package cn.blingfeng.netty.sana.server.handler;

import cn.blingfeng.netty.sana.protocol.SanaRequest;
import cn.blingfeng.netty.sana.util.ExchangetUtil;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class SanaServerHandlerTest {
    @Test
    public void testChannelRead() throws UnknownHostException {
        SanaRequest request = ExchangetUtil.successReq("hello server, i am client");
        EmbeddedChannel channel = new EmbeddedChannel(new SanaServerHandler());
        assertTrue(channel.writeInbound(request));
        assertTrue(channel.finish());
    }
}