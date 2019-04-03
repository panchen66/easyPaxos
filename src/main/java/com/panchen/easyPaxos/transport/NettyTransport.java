package com.panchen.easyPaxos.transport;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.panchen.easyPaxos.core.Node;
import com.panchen.easyPaxos.core.Proposal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTransport {

	private ServerBootstrap sbs;
	private Channel c;

	public NettyTransport(InetSocketAddress InetSocketAddress) throws InterruptedException {
		sbs.channel(NioServerSocketChannel.class);
		sbs.childOption(ChannelOption.SO_KEEPALIVE, true);
		sbs.childOption(ChannelOption.TCP_NODELAY, true);
	}

	public void validate() {
		sbs.validate();
	}

	public void getChannel(SocketAddress socketAddress) {
		c = sbs.bind(socketAddress).channel();
	}

	public void registHandler(Node node) {
		sbs.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast("dispatcher", new SimpleChannelInboundHandler<Object>() {

					@Override
					protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
						node.handle(ctx,msg);
					}

				});
			}

		});
	}

	public void send(Proposal proposal) {
		if (null == c) {
			throw new RuntimeException(" channel is null !");
		}
		c.writeAndFlush(proposal);
	}
}
