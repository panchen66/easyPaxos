package com.panchen.easyPaxos.core;

import io.netty.channel.ChannelHandlerContext;

public interface Acceptor {

    public void listen();

    public void handleProposal(ChannelHandlerContext ctx, PaxosMessage paxosMessage);

    public void handleConfirm(ChannelHandlerContext ctx, PaxosMessage paxosMessage);

}
