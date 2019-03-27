package com.panchen.easyPaxos.core;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

public class PaxosMessage {

    private Long version;
    private byte[] value;

    public PaxosMessage(PaxosMessageHead paxosMessageHead) {
        this.value = Ints.toByteArray(paxosMessageHead.getValue());
        this.version = System.currentTimeMillis();
    }

    public void messageContent(String content) {
        this.value = Bytes.concat(value, content.getBytes());
    }

    public enum PaxosMessageHead {
        P2A(1);

        private int v;

        private PaxosMessageHead(int v) {
            this.v = v;
        }

        public int getValue() {
            return v;
        }
    }


}
