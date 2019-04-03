package com.panchen.easyPaxos.selector;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.panchen.easyPaxos.core.Client;

@Component
public class DefalutSelector implements Selector {

	@Override
	public List<Client> selectAcceptor(List<Client> clients) {
		if (2 >= clients.size()) {
			return clients;
		} else {
			List<Client> acceptors = new ArrayList<Client>();
			for (int i = 1; i < clients.size() / 2 + 1; i++) {
				acceptors.add(clients.get((int) Math.random() * (clients.size() - 1)));
			}
			return acceptors;
		}
	}

}
