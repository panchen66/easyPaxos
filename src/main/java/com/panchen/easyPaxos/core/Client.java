package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class Client extends Node implements Acceptor, Proposer, Learner {

	private List<Client> awakenClient = new LinkedList<Client>();
	private List<Client> waitClient = new ArrayList<Client>();

	private static final int RETRY_TIMES = 3;
	private static final int TIMEOUT = 1000;

	public Client(InetSocketAddress inetSocketAddress) {
		super.inetSocketAddress = inetSocketAddress;
	}

	@Override
	public void handle(ChannelHandlerContext ctx, Object msg) {
		Proposal p = Proposal.ByteBuf2Proposal((ByteBuf) msg);
		if (p.validateWake()) {
			Client client = new Client((InetSocketAddress) ctx.channel().remoteAddress());
			awakenClient.remove(client);
			waitClient.add(client);
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
	public Object register() throws Exception {
		if (null == executor) {
			throw new RuntimeException(" executor is null ！");
		}
		FutureTask<List<Client>> futureTask = new FutureTask<List<Client>>(new Callable<List<Client>>() {

			@Override
			public List<Client> call() throws Exception {
				while (com.panchen.easyPaxos.core.Node.State.WATING != state) {
					for (int i = 1; i <= RETRY_TIMES; i++) {
						Thread.sleep(TIMEOUT * i);
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
	public void listen() {
		// TODO Auto-generated method stub

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
	public void replyToProposer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persistence() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean proposal(Proposal proposal, List<Client> accpeters) {
		// TODO Auto-generated method stub
		return false;
	}

}
