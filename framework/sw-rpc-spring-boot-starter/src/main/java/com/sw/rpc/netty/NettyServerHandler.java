package com.sw.rpc.netty;

import com.alibaba.fastjson.JSON;
import com.sw.rpc.cache.RpcProviderCache;
import com.sw.rpc.domain.SwRpcRequest;
import com.sw.rpc.domain.SwRpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void channelActive(ChannelHandlerContext ctx)   {
        log.info("客户端连接成功!"+ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)   {
        log.info("客户端断开连接!{}",ctx.channel().remoteAddress());
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)   {
        SwRpcRequest request = JSON.parseObject(msg.toString(), SwRpcRequest.class);

        log.info("RPC客户端请求接口:"+request.getClazz()+"   方法名:"+request.getMethodName());
        SwRpcResponse response = new SwRpcResponse();
        response.setRequestId(request.getId());
        try {
            Object result = this.handler(request);
            response.setData(result);
            response.setSuccess(true);
        } catch (Throwable e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setErrorMsg(e.toString());
            log.error("RPC Server handle request error",e);
        }
        ctx.writeAndFlush(JSON.toJSONString(response));
    }

    /**
     * 通过反射，执行本地方法
     * @param request
     * @return
     * @throws Throwable
     */
    private Object handler(SwRpcRequest request) throws Throwable{
        Class<?> className = request.getClazz();
        try {
            Optional<Object> optional = RpcProviderCache.get(className);
            if (!optional.isPresent()) {
                throw new IllegalArgumentException("未找到对应的provider, class:" + className.getSimpleName());
            }
            Object bean = optional.get();
            Class<?> serviceClass = bean.getClass();
            String methodName = request.getMethodName();
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();

            Method method = serviceClass.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(bean, getParameters(parameterTypes, parameters));
        }catch (Exception e) {
            throw new Exception("未找到服务接口,请检查配置!:"+className+"#"+request.getMethodName());
        }
    }

    /**
     * 获取参数列表
     * @param parameterTypes
     * @param parameters
     * @return
     */
    private Object[] getParameters(Class<?>[] parameterTypes, Object[] parameters){
        if (parameters==null || parameters.length==0){
            return parameters;
        }else{
            Object[] new_parameters = new Object[parameters.length];
            for(int i=0;i<parameters.length;i++){
                if (parameterTypes[i] == String.class){
                    new_parameters[i] = parameters[i];
                    continue;
                }
                new_parameters[i] = JSON.parseObject(parameters[i].toString(),parameterTypes[i]);
            }
            return new_parameters;
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)   {
        log.info(cause.getMessage());
        ctx.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
