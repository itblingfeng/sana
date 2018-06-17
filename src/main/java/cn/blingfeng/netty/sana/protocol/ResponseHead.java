package cn.blingfeng.netty.sana.protocol;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseHead {
    //协议名
    private String protocolName = "sana";
    //协议版本
    private int protocolVersion = 1;
    //服务器版本
    private String serverVersion = "Netty 4.0";
    //状态码
    private int code;
    //时间
    private Date date = new Date();

    public ResponseHead(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("-> protocol name :").append(protocolName + "\n")
                .append("-> protocol version :").append(protocolVersion + "\n")
                .append("-> code :").append(code + "\n")
                .append("-> time :").append(date.toString() + "\n").toString();
    }
}
