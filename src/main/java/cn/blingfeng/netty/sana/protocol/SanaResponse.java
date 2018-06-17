package cn.blingfeng.netty.sana.protocol;

import io.netty.util.CharsetUtil;
import lombok.Data;

@Data
public class SanaResponse {
    //响应头
    private ResponseHead resHead;
    //响应体  1024
    private String resBody;

    public SanaResponse(ResponseHead resHead, String resBody) {
        this.resHead = resHead;
        this.resBody = resBody;
    }

    @Override
    public String toString() {
        return resHead.toString() + resBody;
    }
}
