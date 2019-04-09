package com.panchen.easyPaxos.future;

import java.util.concurrent.Callable;

import com.panchen.easyPaxos.core.PaxosMessage;

public abstract class PaxosCallable implements Callable<Boolean> {

	private PaxosMessage paxosMessage;

	public PaxosCallable(PaxosMessage paxosMessage) {
		this.paxosMessage = paxosMessage;
	}

	@Override
	public Boolean call() throws Exception {
		return process();
	}

	protected abstract Boolean process();

}
