package com.panchen.easyPaxos.core;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.net.InetAddresses;
import com.panchen.easyPaxos.transport.NettyTransport;

public abstract class Node {

    private static State state = State.WATING;
    private NettyTransport nettyTransport;
    private InetAddresses inetAddresses;
    private ConcurrentLinkedQueue<PaxosMessage> paxosMessageQueue = new ConcurrentLinkedQueue<PaxosMessage>();


    private List<Acceptor> choiceAcceptors() {
        return null;

    }

    private List<Learner> choiceLearners() {
        return null;

    }

    private List<Proposer> choiceProposers() {
        return null;

    }

    protected abstract void handlePaxosMessage(PaxosMessage paxosMessage);

    private enum State {
        WATING, PREPARE, RESPONSE
    }

    private class recMessageThread extends Thread {

        @Override
        public void run() {
            while (com.panchen.easyPaxos.core.Node.State.WATING != state) {
                if (null != paxosMessageQueue.peek()) {
                    handlePaxosMessage(paxosMessageQueue.poll());
                }
            }
        }

    }
}
