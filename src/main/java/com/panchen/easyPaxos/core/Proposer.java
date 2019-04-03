package com.panchen.easyPaxos.core;

import java.util.List;

public interface Proposer {

	public boolean proposal(Proposal proposal, List<Client> accpeters);

	public void explicit();

	public void replyToAcceptor();
}
