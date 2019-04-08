package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.panchen.easyPaxos.transport.NettyTransport;

public abstract class Node {

	public static State state = State.WATING;
	public NettyTransport nettyTransport;
	public InetSocketAddress inetSocketAddress;
	protected ThreadPoolExecutor executor;
	private static final int DEFALUT_COREPOOLSIZE = 1;
	
	public enum State {
		WATING, PREPARE, RESPONSE
	}


	public void initThreadPoolExecutor(int corePoolSize) {
		if (0 >= corePoolSize) {
			executor = new ThreadPoolExecutor(DEFALUT_COREPOOLSIZE, DEFALUT_COREPOOLSIZE * 2, 30, TimeUnit.MINUTES,
					new ArrayBlockingQueue<Runnable>(10));
		}
		executor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, 30, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(10));
	}

	public abstract void persistence();

	protected void recoveryExecutor() {
		executor.shutdown();
	}

}