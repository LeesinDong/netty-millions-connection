package netty.connection;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Tom.
 */
public class Client {

    private static final String SERVER_HOST = "127.0.0.1";

    public static void main(String[] args) {
        new Client().start(Server.BEGIN_PORT, Server.N_PORT);
    }

    public void start(final int beginPort, int nPort) {
        System.out.println("client starting....");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
            }
        });


        int index = 0;
        int port;
        //只要程序不停，就一直尝试添加客户端连接到服务端。
        //服务端不需要生命端口，连接一次消耗一个本地的端口。 一共消耗60000个，也就是一共能创建60000个
        //60000*20个服务端端口=120 0000
        while (!Thread.interrupted()) {
            port = beginPort + index;
            try {
                ChannelFuture channelFuture = bootstrap.connect(SERVER_HOST, port);
                channelFuture.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            System.out.println("connect failed, exit!");
                            System.exit(0);
                        }
                    }
                });
                channelFuture.get();
            } catch (Exception e) {
            }

            if (port == nPort) { index = 0; }else { index ++; }
        }
    }
}
