package com.panchen.easyPaxos.core;

import java.util.List;

public interface Proposer {

	public boolean proposal(PaxosMessage paxosMessage, List<Client> accpeters);

	public void explicit();

	public void replyToAcceptor();
}
