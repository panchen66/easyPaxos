package com.panchen.easyPaxos.future;

public class ProposerCallable extends PaxosCallable {

    public ProposerCallable(PaxosMessage paxosMessage) {
        super(paxosMessage);
    }

    @Override
    protected Boolean process() {
        return null;
    }

}
