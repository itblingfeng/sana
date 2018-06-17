package cn.blingfeng.netty.sana.util;

import cn.blingfeng.netty.sana.protocol.RequestHead;
import cn.blingfeng.netty.sana.protocol.ResponseHead;
import cn.blingfeng.netty.sana.protocol.SanaRequest;
import cn.blingfeng.netty.sana.protocol.SanaResponse;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class ExchangetUtil {
    public static SanaRequest successReq(String reqBody) throws UnknownHostException {
        byte[] body = reqBody.getBytes();
        return new SanaRequest(new RequestHead(InetAddress.getLocalHost().toString(), body.length), new String(body, CharsetUtil.UTF_8));
    }

    public static SanaResponse successRes(String reqBody) {
        return new SanaResponse(new ResponseHead(CodeConstant.REQ_SUCCESS), new String(reqBody.getBytes(), CharsetUtil.UTF_8));
    }
}
