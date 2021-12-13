package netty01;

/**
 * 53 * 自定义Handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 54
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     58 * 读取客户端发送的数据
     59 *
     60 * @param ctx 上下文对象, 含有通道channel，管道pipeline
     61 * @param msg 就是客户端发送的数据
     62 * @throws Exception
     63 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        //Channel channel = ctx.channel();
        //ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接, 出站入站
        //将 msg 转成一个 ByteBuf，类似NIO 的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
    }

    /**
     75 * 数据读取完毕处理方法
     76 *
     77 * @param ctx
     78 * @throws Exception
     79 */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    /**
     87 * 处理异常, 一般是需要关闭通道
     88 *
     89 * @param ctx
     90 * @param cause
     91 * @throws Exception
     92 */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}