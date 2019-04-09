package com.panchen.easyPaxos.core;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public interface Proposer {

	public boolean proposal(PaxosMessage paxosMessage, List<Client> accpeters);

	public boolean confirm(PaxosMessage paxosMessage, List<Client> accpeters);

	public void handleReplyProposal(ChannelHandlerContext ctx, PaxosMessage paxosMessage);

	public void handleReplyConfirm(ChannelHandlerContext ctx, PaxosMessage paxosMessage);
}
