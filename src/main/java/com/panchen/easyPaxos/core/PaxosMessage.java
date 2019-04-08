package com.panchen.easyPaxos.core;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import io.netty.buffer.ByteBuf;

public class PaxosMessage {

	private Long version;
	private byte[] content;

	public PaxosMessage(PaxosMHead proposalHead, String key, String value) {
		this.content = Ints.toByteArray(proposalHead.getValue());
		this.content = Bytes.concat(content, key.getBytes(), value.getBytes());
		this.version = System.currentTimeMillis();
	}

	public enum PaxosMHead {

		PROPOSER_PROPOSE(1),

		PROPOSER_CONFIRM(2),

		ACCEPTOR_REPLYCONFIRM(3),

		PROPOSER_REPLYACCEPTORCONFIRM(4),

		ACCEPTOR_APPROVAL(5);

		private int v;

		private PaxosMHead(int v) {
			this.v = v;
		}

		public int getValue() {
			return v;
		}

		public static PaxosMHead getHeadByV(int v) {
			for (PaxosMHead paxosMHead : PaxosMHead.values()) {
				if (v == paxosMHead.getValue()) {
					return paxosMHead;
				}
			}
			return null;
		}
	}

	public static PaxosMessage ByteBuf2Proposal(ByteBuf buffer) {
		return null;
	}

	public PaxosMHead getHead() {
		return PaxosMHead.getHeadByV(Ints.fromBytes(content[0], content[1], content[2], content[3]));
	}

	public Long version() {
		return Longs.fromBytes(content[4], content[5], content[6], content[7], content[8], content[9], content[10],
				content[11]);
	}

	public int capacity() {
		return content.length;
	}

}
