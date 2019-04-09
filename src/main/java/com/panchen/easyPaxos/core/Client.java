package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panchen.easyPaxos.core.PaxosMessage.PaxosMHead;
import com.panchen.easyPaxos.future.PaxosFutrueTask;
import com.panchen.easyPaxos.persistence.FilePersistent;

import io.netty.channel.ChannelHandlerContext;

@Component
public class Client extends Node implements Acceptor, Proposer, Learner {

	@Autowired
	FilePersistent filePersistent;

	private List<Client> acceptor;

	private static final int WAKE_RETRY_TIMES = 3;
	private static final int WAKE_TIMEOUT = 1000;

	// acceptor
	private volatile Long acceptorVersion;

	// proposer
	private volatile Long proposerVersion;
	private AtomicInteger proposerCount;
	private AtomicInteger confirmCount;
	private List<Client> votedAcceptor;
	private List<Client> confirmAcceptor;

	public Client(InetSocketAddress inetSocketAddress) {
		super.inetSocketAddress = inetSocketAddress;
	}

	public class PaxosTaskHandler extends Thread {

		private BlockingQueue<PaxosFutrueTask> taskQueue;
		private ThreadPoolExecutor executor;

		PaxosTaskHandler(BlockingQueue<PaxosFutrueTask> taskQueue, ThreadPoolExecutor executor) {
			this.taskQueue = taskQueue;
			this.executor = executor;
		}

		public void run() {
			while (null != taskQueue.peek()) {
				Future<?> future = executor.submit(taskQueue.poll());
			}
		}

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Client) {
			Client c = (Client) o;
			return this.inetSocketAddress.getAddress().getHostAddress()
					.equals(c.inetSocketAddress.getAddress().getHostAddress());
		}
		return false;

	}

	@Override
	public void learn() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean proposal(PaxosMessage paxosMessage, List<Client> accpeters) {
		// send proposal
		for (Client accpeter : accpeters) {
			nettyTransport.send(accpeter.inetSocketAddress, paxosMessage);
		}

		return false;
	}

	@Override
	public void replyToLearn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void listen() {
		nettyTransport.registProposerAndAcceptorHandler();
	}

	@Override
	public void handleProposal(ChannelHandlerContext ctx, PaxosMessage paxosMessage) {
		if (acceptorVersion >= paxosMessage.version()) {
			return;
		}
		if (filePersistent.serialize(paxosMessage)) {
			acceptorVersion = paxosMessage.version();
			paxosMessage.head(PaxosMHead.ACCEPTOR_REPLYAPPROVAL);
			nettyTransport.send(ctx, paxosMessage);
		}
	}

	@Override
	public void handleConfirm(ChannelHandlerContext ctx, PaxosMessage paxosMessage) {
		if (acceptorVersion > paxosMessage.version()) {
			nettyTransport.send(ctx, new PaxosMessage(filePersistent.deserialize()));
		} else {
			paxosMessage.head(PaxosMHead.ACCEPTOR_REPLYCONFIRM);
			nettyTransport.send(ctx, paxosMessage);
		}

	}

	@Override
	public void handleReplyProposal(ChannelHandlerContext ctx, PaxosMessage paxosMessage) {
		if (proposerVersion == paxosMessage.version()) {
			proposerCount.incrementAndGet();
		}
		if (proposerCount.intValue() >= acceptor.size() / 2 + 1) {
			confirm(paxosMessage, acceptor);
		}
	}

	@Override
	public void handleReplyConfirm(ChannelHandlerContext ctx, PaxosMessage paxosMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean confirm(PaxosMessage paxosMessage, List<Client> accpeters) {
		for (Client accpeter : accpeters) {
			nettyTransport.send(accpeter.inetSocketAddress, paxosMessage);
		}

		//
		return false;
	}

}
