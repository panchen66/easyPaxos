package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.panchen.easyPaxos.transport.NettyTransport;

import io.netty.channel.ChannelHandlerContext;

public abstract class Node {

	public static State state = State.WATING;
	public NettyTransport nettyTransport;
	public InetSocketAddress inetSocketAddress;
	protected ThreadPoolExecutor executor;
	private static final int DEFALUT_COREPOOLSIZE = 1;
	private ConcurrentLinkedQueue<Proposal> proposalQueue = new ConcurrentLinkedQueue<Proposal>();
	
	public enum State {
		WATING, PREPARE, RESPONSE
	}

	private class sendProposalThread extends Thread {

		@Override
		public void run() {
			while (com.panchen.easyPaxos.core.Node.State.WATING != state) {
				if (null != proposalQueue.peek()) {
				}
			}
		}

	}

	public NettyTransport createNettyTransport() {
		try {
			this.nettyTransport = new NettyTransport(inetSocketAddress);
			return nettyTransport;
		} catch (InterruptedException e) {
			throw new RuntimeException("createNettyTransport error : " + e);
		}
	}

	public abstract void handle(ChannelHandlerContext ctx, Object msg);

	public void initThreadPoolExecutor(int corePoolSize) {
		if (0 >= corePoolSize) {
			executor = new ThreadPoolExecutor(DEFALUT_COREPOOLSIZE, DEFALUT_COREPOOLSIZE * 2, 30, TimeUnit.MINUTES,
					new ArrayBlockingQueue<Runnable>(10));
		}
		executor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, 30, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(10));
	}

	public abstract Object register() throws Exception;

	public abstract void persistence();

	protected void recoveryExecutor() {
		executor.shutdown();
	}

}