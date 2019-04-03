package com.panchen.easyPaxos.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panchen.easyPaxos.core.Proposal.ProposalHead;
import com.panchen.easyPaxos.selector.Selector;
import com.panchen.easyPaxos.transport.NettyTransport;

@Component
public class Cluster {

	@Autowired
	public Selector selector;

	private Client local;

	private List<Client> clients;
	private List<Client> acceptors;

	@SuppressWarnings("unchecked")
	public void wake() {
		local = clients.get(0);
		NettyTransport nettyTransport = local.createNettyTransport();
		nettyTransport.registHandler(local);
		try {
			clients = (List<Client>) local.register();
			clients.add(local);
		} catch (Exception e) {
		}
	}

	public void joinCluster(Client client) {
		clients.add(client);
	}

	public boolean proposal(String key, String value) {
		return local.proposal(new Proposal(ProposalHead.P2A, key, value), acceptors);

	}

	public void select() {
		acceptors = selector.selectAcceptor(clients);
		// if local is acceptor
		if (acceptors.contains(local)) {

		}
	}
}
