package org.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

public class ServerClientHandler extends ChannelHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(ServerClientHandler.class.getName());

	private final ByteBuf firstMessage;

	private ChannelHandlerContext chc;

	private String text;

	/**
	 * Creates a client-side handler.
	 */
	public ServerClientHandler(String text,ChannelHandlerContext chc) {
		this.chc = chc;
		this.text = text;
		byte[] req = text.getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(firstMessage);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("来自服务端的数据: " + body);
		//ClientServerHandler.map.put(text,body);
		ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
		chc.writeAndFlush(resp);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.warning("Unexpected exception from downstream : "
				+ cause.getMessage());
		ctx.close();
	}
}
