package org.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.hash.OneHash;

public class ClientServerHandler extends ChannelHandlerAdapter {

//    public static HashMap<String,String> map = new HashMap<>();
//
//    public static AtomicInteger inc = new AtomicInteger(0);
//
//    public void increase(){
//        inc.getAndAdd(1);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("来自客户端的数据: " + body);
        int a = Integer.parseInt(body);
        while(null==OneHash.map.get(a)){
            a++;
            if(a==65536){
                a=0;
            }
        }
        OneHash.map.put(Integer.parseInt(body),OneHash.map.get(a));
        new ServerClient(body,ctx).connect(OneHash.map.get(a),"127.0.0.1");
//        if(null==map.get(body)){
//            if (inc.get() % 9 == 0 || inc.get() % 9 == 1 || inc.get() % 9 == 4) {
//                increase();
//                new ServerClient(body, ctx).connect(8080, "127.0.0.1");
//            } else if (inc.get() % 9 == 2 || inc.get() % 9 == 3 || inc.get() % 9 == 6 || inc.get() % 9 == 7) {
//                increase();
//                new ServerClient(body, ctx).connect(8081, "127.0.0.1");
//            } else if (inc.get() % 9 == 5 || inc.get() % 9 == 8) {
//                increase();
//                new ServerClient(body, ctx).connect(8082, "127.0.0.1");
//            }
//        } else {
//            ByteBuf resp = Unpooled.copiedBuffer(map.get(body).getBytes());
//            ctx.writeAndFlush(resp);
//            ctx.close();
//        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

}
