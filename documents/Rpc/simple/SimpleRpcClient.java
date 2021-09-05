package Rpc.simple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SimpleRpcClient{

    public static Object proxy(Class<?> clazz){
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(bytes);
                objectOutputStream.write("你好".getBytes(StandardCharsets.UTF_8));
                return null;
            }
        });
    }
}
