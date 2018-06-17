package cn.blingfeng.netty.sana.serialize.protostuff;

import cn.blingfeng.netty.sana.protocol.SanaRequest;
import cn.blingfeng.netty.sana.protocol.SanaResponse;
import cn.blingfeng.netty.sana.serialize.Serialize;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

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
}
