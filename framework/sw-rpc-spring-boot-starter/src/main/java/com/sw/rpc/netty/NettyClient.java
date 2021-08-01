package com.sw.rpc.netty;

import com.alibaba.fastjson.JSONArray;
import com.sw.rpc.domain.ServiceMateData;
import com.sw.rpc.domain.SwRpcRequest;
import com.sw.rpc.domain.SwRpcResponse;
import com.sw.rpc.register.cache.ServiceMateDataCache;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;

@Slf4j
@Component
public class NettyClient {

    private EventLoopGroup group = new NioEventLoopGroup(1);
    private Bootstrap bootstrap = new Bootstrap();

    @Autowired
    NettyClientHandler clientHandler;

//    @Autowired
//    ConnectManage connectManage;


    public NettyClient(){
        bootstrap.group(group).
                channel(NioSocketChannel.class).
                option(ChannelOption.TCP_NODELAY, true).
                option(ChannelOption.SO_KEEPALIVE,true).
                handler(new ChannelInitializer<SocketChannel>() {
                    //创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 0, 30));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(clientHandler);
                    }
                });
    }

    @PreDestroy
    public void destroy(){
        log.info("RPC客户端退出,释放资源!");
        group.shutdownGracefully();
    }

    public Object send(SwRpcRequest request) throws InterruptedException{

        Optional<ServiceMateData> optional = ServiceMateDataCache.get(request.getClazz().getName());
        if (!optional.isPresent()) {
            return createFailedResponse();
        }
        Optional<Channel> channelOptional = ConnectManageCache.get(optional.get().getServiceId());
        if (channelOptional.isPresent() && channelOptional.get().isActive()) {
            SynchronousQueue<Object> queue = clientHandler.sendRequest(request, channelOptional.get());
            Object result = queue.take();
            return JSONArray.toJSONString(result);
        }else{
            SwRpcResponse res = new SwRpcResponse();
            res.setSuccess(false);
            res.setErrorMsg("未正确连接到服务器.请检查相关配置信息!");
            return JSONArray.toJSONString(res);
        }
    }

    private String createFailedResponse(){
        SwRpcResponse res = new SwRpcResponse();
        res.setSuccess(false);
        res.setErrorMsg("未正确连接到服务器.请检查相关配置信息!");
        return JSONArray.toJSONString(res);
    }

    public Channel doConnect(SocketAddress address) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(address);
        Channel channel = future.sync().channel();
        return channel;
    }
}
