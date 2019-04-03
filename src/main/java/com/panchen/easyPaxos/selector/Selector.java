package com.panchen.easyPaxos.selector;

import java.util.List;

import com.panchen.easyPaxos.core.Client;

public interface Selector {

	public List<Client> selectAcceptor(List<Client> clients);

}
