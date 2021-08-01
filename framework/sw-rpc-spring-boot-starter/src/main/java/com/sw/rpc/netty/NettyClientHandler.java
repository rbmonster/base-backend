package com.sw.rpc.netty;

import com.alibaba.fastjson.JSON;
import com.sw.rpc.domain.SwRpcRequest;
import com.sw.rpc.domain.SwRpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    NettyClient client;

//    @Autowired
//    ConnectManage connectManage;

    private ConcurrentHashMap<String, SynchronousQueue<Object>> queueMap = new ConcurrentHashMap<>();

    public void channelActive(ChannelHandlerContext ctx) {
        log.info("已连接到RPC服务器.{}",ctx.channel().remoteAddress());
    }

    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress address =(InetSocketAddress) ctx.channel().remoteAddress();
        log.info("与RPC服务器断开连接."+address);
        ctx.channel().close();
//        connectManage.removeChannel(ctx.channel());
    }

    /**
     * 接收服务端返回信息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        SwRpcResponse response = JSON.parseObject(msg.toString(),SwRpcResponse.class);
        String requestId = response.getRequestId();
        SynchronousQueue<Object> queue = queueMap.get(requestId);
        queue.put(response);
        queueMap.remove(requestId);
    }

    public SynchronousQueue<Object> sendRequest(SwRpcRequest request, Channel channel) {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        queueMap.put(request.getId(), queue);
        channel.writeAndFlush(request);
        return queue;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.info("RPC通信服务器发生异常.{}",cause);
        ctx.channel().close();
    }
}
