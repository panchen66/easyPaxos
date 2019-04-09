package com.panchen.easyPaxos.future;

import com.panchen.easyPaxos.core.PaxosMessage;

public class ProposerCallable extends PaxosCallable {

	public ProposerCallable(PaxosMessage paxosMessage) {
		super(paxosMessage);
	}

	@Override
	protected Boolean process() {
		return null;
	}

}
