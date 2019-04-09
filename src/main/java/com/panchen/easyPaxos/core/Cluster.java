package com.panchen.easyPaxos.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.panchen.easyPaxos.core.PaxosMessage.PaxosMHead;
import com.panchen.easyPaxos.selector.Selector;

@Component
public class Cluster {

	@Autowired
	public Selector selector;

	@Autowired
	private Client local;

	private List<Client> clients;
	private List<Client> acceptors;

	public void joinCluster(Client client) {
		clients.add(client);
	}

	public boolean proposal(String key, String value) {
		return local.proposal(new PaxosMessage(PaxosMHead.PROPOSER_PROPOSE, key, value), acceptors);

	}

	public void select() {
		acceptors = selector.selectAcceptor(clients);

		if (acceptors.contains(local)) {
			local.listen();
		}
	}
}
