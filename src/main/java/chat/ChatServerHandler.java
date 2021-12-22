package chat;

/**
 * 53 * 自定义Handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 1 channelRead0 会自动接收客户端发送的消息，处理后再发送给客户端。
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup =new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   //表示channel处于就绪状态，提示上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入的聊天信息会发送给其他在线客户端
        //该方法会把所有的channelGroup中的所有的channel遍历，并发送消息
        channelGroup.writeAndFlush("客户端"+ channel.remoteAddress()+"上线了1"+sf.format(new java.util.Date())+ "\n");
        //将当前channel加入channelgroup
        channelGroup.add(channel);
        System.out.println(ctx.channel().remoteAddress()+"上线了2"+"\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端"+ channel.remoteAddress()+"下线了3"+sf.format(new java.util.Date())+ "\n");
        System.out.println(ctx.channel().remoteAddress()+"下线了4"+"\n");
        System.out.println(channelGroup.size());
    }



  /*  *//**
     58 * 读取客户端发送的数据
     59 *
     60 * @param ctx 上下文对象, 含有通道channel，管道pipeline
     61 * @param msg 就是客户端发送的数据
     62 * @throws Exception
     63 *//*
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        //Channel channel = ctx.channel();
        //ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接, 出站入站
        //将 msg 转成一个 ByteBuf，类似NIO 的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
    }
    *//**
     75 * 数据读取完毕处理方法
     76 *
     77 * @param ctx
     78 * @throws Exception
     79 *//*
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    *//**
     87 * 处理异常, 一般是需要关闭通道
     88 *
     89 * @param ctx
     90 * @param cause
     91 * @throws Exception
     92 *//*
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
//获取当前channel
        Channel channel = ctx.channel();

//这个时候我们遍历channelGroup 根据不同的情况 ，回送不同的消息

        channelGroup.forEach(ch->{
            if(ch!=channel){
ch.writeAndFlush("客户端"+ channel.remoteAddress()+"发送了消息5"+s+ "\n");
            }else{

                ch.writeAndFlush("自己"+ channel.remoteAddress()+"发送了消息6"+s+ "\n");

            }


        });



    }


}