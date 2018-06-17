package cn.blingfeng.netty.sana.protocol;

import lombok.Data;

@Data
public class RequestHead {
    //协议名
    private String protocolName = "sana";
    //协议版本
    private int protocolVersion = 1;
    //host
    private String host;
    //请求体长度
    private int contentLength;

    public RequestHead(String host, int contentLength) {
        this.host = host;
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("-> protocol name :").append(protocolName + "\n")
                .append("-> protocol version :").append(protocolVersion + "\n")
                .append("-> host :").append(host + "\n")
                .append("-> contentLength :").append(contentLength + "\n").toString();
    }
}
