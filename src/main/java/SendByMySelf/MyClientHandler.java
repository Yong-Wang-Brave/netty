package SendByMySelf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i <5 ; i++) {
            String  msg="你好，我是张三。";//utf8 汉字占用3个字节 总共是24个字节
            //创建协议包对象
            MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
            myMessageProtocol.setLen(msg.getBytes(CharsetUtil.UTF_8).length);
            myMessageProtocol.setContent(msg.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(myMessageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessageProtocol msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
