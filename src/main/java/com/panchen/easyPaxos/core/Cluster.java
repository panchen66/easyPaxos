package com.panchen.easyPaxos.core;

import java.util.List;

import com.panchen.easyPaxos.transport.NettyTransport;

public class Cluster {

	private List<Client> clients;
	public Selector selector;

	@SuppressWarnings("unchecked")
	public void wake() {
		Client local = clients.get(0);
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
}
