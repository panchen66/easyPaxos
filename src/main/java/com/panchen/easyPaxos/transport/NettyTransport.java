package com.panchen.easyPaxos.transport;

import java.net.SocketAddress;

import com.panchen.easyPaxos.core.Client;
import com.panchen.easyPaxos.core.PaxosMessage;
import com.panchen.easyPaxos.core.PaxosMessage.PaxosMHead;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTransport {

	private static ServerBootstrap sbs;
	private Client client;

	public NettyTransport(Client client) throws InterruptedException {
		this.client = client;

		sbs.channel(NioServerSocketChannel.class);
		sbs.localAddress(client.inetSocketAddress);
		sbs.childOption(ChannelOption.SO_KEEPALIVE, true);
		sbs.childOption(ChannelOption.TCP_NODELAY, true);
	}

	public void validate() {
		sbs.validate();
	}

	private static Channel getChannel(SocketAddress socketAddress) {
		return sbs.bind(socketAddress).channel();
	}

	public void registProposerAndAcceptorHandler() {
		sbs.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast("dispatcher", new SimpleChannelInboundHandler<Object>() {

					@Override
					protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

						PaxosMessage paxosMessage = PaxosMessage.ByteBuf2Proposal((ByteBuf) msg);
						PaxosMHead paxosMHead = paxosMessage.getHead();

						// listen proposal
						if (paxosMHead.equals(PaxosMHead.PROPOSER_PROPOSE)) {
							client.handlePropose(ctx, paxosMessage);
						}
						
						// listen confirm
						if (paxosMHead.equals(PaxosMHead.PROPOSER_CONFIRM)) {

						}

						// get replyToAcceptor

						//
					}

				});
			}

		});
	}

	public void registProposerHandler() {
		sbs.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast("dispatcher", new SimpleChannelInboundHandler<Object>() {

					@Override
					protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

						PaxosMessage paxosMessage = PaxosMessage.ByteBuf2Proposal((ByteBuf) msg);
						PaxosMHead paxosMHead = paxosMessage.getHead();

					}

				});
			}

		});
	}

	public void send(ChannelHandlerContext ctx, PaxosMessage paxosMessage) {
		Channel c = getChannel(ctx.channel().remoteAddress());
		c.writeAndFlush(paxosMessage);
	}

	public void send(SocketAddress socketAddress, PaxosMessage paxosMessage) {
		Channel c = getChannel(socketAddress);
		c.writeAndFlush(paxosMessage);
	}
}
