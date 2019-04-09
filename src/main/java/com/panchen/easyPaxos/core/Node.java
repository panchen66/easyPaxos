package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.panchen.easyPaxos.future.PaxosFutrueTask;
import com.panchen.easyPaxos.transport.NettyTransport;

public abstract class Node {

	public static State state = State.WATING;
	public NettyTransport nettyTransport;
	public InetSocketAddress inetSocketAddress;
	protected ThreadPoolExecutor executor;
	public static BlockingQueue<PaxosFutrueTask> proposerTaskQueue = new ArrayBlockingQueue<PaxosFutrueTask>(1024);
	public static BlockingQueue<PaxosFutrueTask> confirmTaskQueue = new ArrayBlockingQueue<PaxosFutrueTask>(1024);

	public enum State {
		WATING, PREPARE, RESPONSE
	}

	public void initThreadPoolExecutor(int size) {
		executor = new ThreadPoolExecutor(size, size, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10));
	}

	protected void recoveryExecutor() {
		executor.shutdown();
	}

}