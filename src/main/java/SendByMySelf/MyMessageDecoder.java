package SendByMySelf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyMessageDecoder extends ByteToMessageDecoder
{
    int length=0;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println();
        System.out.println("MyMessageDecoder decode 被调用");
        //需要将得到二进制字节码-》MyMessafeProtocol数据包（对象）
        System.out.println(in);
        //长度的单位是int，占用4个字节，客户端会把长度 先发过来
        if ((in.readableBytes()>=4)) {
            if ((length==0)) {
              length=  in.readInt();
            }

            if (in.readableBytes()<length) {
                System.out.println("当前可读数据不够，继续等待。。");
                return;
            }
            byte[] content = new byte[length];
            if (in.readableBytes()>=length) {
                in.readBytes(content);

                //封装成MyMessageProtocol对象，传递下一个handler业务处理
                MyMessageProtocol messageProtocol = new MyMessageProtocol();
                messageProtocol.setLen(length);

                messageProtocol.setContent(content);
                out.add(messageProtocol);

            }
            length=0;


        }



    }
}
