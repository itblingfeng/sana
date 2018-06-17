package cn.blingfeng.netty.sana.codec;

public class CodecConstant {
    //消息长度 1 个字节
    public static final int MESSAGE_TYPE_LENGTH = 1;
    //消息类型  0：request   1：response
    public static final int MESSAGE_TYPE_REQ = 0;
    public static final int MESSAGE_TYPE_RES = 1;
    //发送消息总长度
    public static final int MESSAGE_LENGTH = 4;
}
