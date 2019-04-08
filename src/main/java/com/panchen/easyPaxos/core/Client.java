package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panchen.easyPaxos.persistence.FilePersistent;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Component
public class Client extends Node implements Acceptor, Proposer, Learner {

	@Autowired
	FilePersistent filePersistent;

	private List<Client> awakenClient = new LinkedList<Client>();
	private List<Client> waitClient = new ArrayList<Client>();

	private static final int WAKE_RETRY_TIMES = 3;
	private static final int WAKE_TIMEOUT = 1000;

	// acceptor
	private volatile Long lastVersion;
	private volatile 
	
	public Client(InetSocketAddress inetSocketAddress) {
		super.inetSocketAddress = inetSocketAddress;
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

	public Object registerWake() throws Exception {
		if (null == executor) {
			throw new RuntimeException(" executor is null ！");
		}
		FutureTask<List<Client>> futureTask = new FutureTask<List<Client>>(new Callable<List<Client>>() {

			@Override
			public List<Client> call() throws Exception {
				while (com.panchen.easyPaxos.core.Node.State.WATING != state) {
					for (int i = 1; i <= WAKE_RETRY_TIMES; i++) {
						Thread.sleep(WAKE_TIMEOUT * i);
						if (0 == awakenClient.size()) {
							return waitClient;
						}
					}
					return waitClient;
				}
				return null;
			}

		});
		return futureTask.get();

	}

	@Override
	public void learn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void explicit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void replyToAcceptor() {
		// TODO Auto-generated method stub

	}

	@Override
	public void persistence() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean proposal(PaxosMessage paxosMessage, List<Client> accpeters) {
		// send proposal
		for (Client c : accpeters) {
		}

		// register

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
		if (paxosMessage.version() <= lastVersion) {
			return;
		}
		if (filePersistent.serialize(paxosMessage)) {
			lastVersion = paxosMessage.version();
			nettyTransport.send(ctx, paxosMessage);
		}
	}

	@Override
	public void handleConfirm(ChannelHandlerContext ctx, PaxosMessage paxosMessage) {
	}

}
