package com.panchen.easyPaxos.core;

public interface Acceptor {

	public void listen();

	public void replyToProposer();
}
