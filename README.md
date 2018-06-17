# sana
<h2>sana is a netty build custom protocol</h2>

![Alt text](https://github.com/itblingfeng/img-folder/blob/master/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-06-17%20%E4%B8%8B%E5%8D%8811.10.10.png)
![Alt text](https://github.com/itblingfeng/img-folder/blob/master/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-06-17%20%E4%B8%8B%E5%8D%8811.10.24.png)

<h2>About</h2>
Use SanaRequest and SanaResponse between client and server communication

```java
@Data
public class SanaRequest {
    //请求头
    private RequestHead reqHead;
    //请求体
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
```

```java
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
```
client and server use Protostuff for serialization

```java
@SuppressWarnings("unchecked")
public class ProtostuffSerialize implements Serialize {
    private final static Map<Class<?>, Schema<?>> cacheSchemas = new ConcurrentHashMap<>(2);
    private static Objenesis objenesis = new ObjenesisStd(true);
    private final static int DEFAULT_BUFFER_SIZE = 256;
    private final static ReentrantLock LOCK = new ReentrantLock();

    @Override
    public void serialize(OutputStream out, Object obj) {
        Class type = obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(type);
            ProtostuffIOUtil.writeTo(out, obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public Object deserialize(InputStream in, boolean isReq) {
        try {
            Class type = Boolean.TRUE.equals(isReq) ? SanaRequest.class : SanaResponse.class;
            Object obj = objenesis.newInstance(type);
            Schema<Object> schema = getSchema(type);
            ProtostuffIOUtil.mergeFrom(in, obj, schema);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private <T> Schema<T> getSchema(Class<T> type) {
        Schema schema = cacheSchemas.get(type);
        if (schema == null) {
            LOCK.lock();
            try {
                schema = cacheSchemas.get(type);
                if (schema == null) {
                    schema = RuntimeSchema.getSchema(type);
                    cacheSchemas.put(type, schema);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return schema;
    }
```

<h2>Operation result</h2>

![Alt text](https://github.com/itblingfeng/img-folder/blob/master/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-06-17%20%E4%B8%8B%E5%8D%8811.23.46.png)
![Alt text](https://github.com/itblingfeng/img-folder/blob/master/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-06-17%20%E4%B8%8B%E5%8D%8811.23.58.png)

