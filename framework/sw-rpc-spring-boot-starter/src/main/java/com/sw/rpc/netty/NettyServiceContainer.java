package com.sw.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NettyServiceContainer {

    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(4);

    @Value("${sw.netty.host}")
    private String host;

    @Value("${sw.netty.port}")
    private int port;


    public void start() {
        final NettyServerHandler serverHandler = new NettyServerHandler();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup).
                    channel(NioServerSocketChannel.class).
                    option(ChannelOption.SO_BACKLOG,1024).
                    childOption(ChannelOption.SO_KEEPALIVE,true).
                    childOption(ChannelOption.TCP_NODELAY,true).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new IdleStateHandler(0, 0, 60));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(serverHandler);
                        }
                    });

            ChannelFuture cf = bootstrap.bind(host, port).sync();
            log.info("RPC 服务器启动.监听端口:"+port);
            //等待服务端监听端口关闭
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
