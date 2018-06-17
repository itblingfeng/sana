package cn.blingfeng.netty.sana.protocol;

import io.netty.util.CharsetUtil;
import lombok.Data;

@Data
public class SanaRequest {
    //请求头
    private RequestHead reqHead;
    //请求体 1024
    private String reqBody;

    public SanaRequest(RequestHead reqHead, String reqBody) {
        this.reqHead = reqHead;
        this.reqBody = reqBody;
    }

    @Override
    public String toString() {
        return reqHead.toString() + reqBody;
    }
}
