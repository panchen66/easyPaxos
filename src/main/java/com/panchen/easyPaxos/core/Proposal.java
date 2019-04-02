package com.panchen.easyPaxos.core;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

import io.netty.buffer.ByteBuf;

public class Proposal {

	private Long version;
	private byte[] value;

	public Proposal(ProposalHead proposalHead) {
		this.value = Ints.toByteArray(proposalHead.getValue());
		this.version = System.currentTimeMillis();
	}

	public void messageContent(String content) {
		this.value = Bytes.concat(value, content.getBytes());
	}

	public enum ProposalHead {
		P2A(1);

		private int v;

		private ProposalHead(int v) {
			this.v = v;
		}

		public int getValue() {
			return v;
		}
	}

	public static Proposal ByteBuf2Proposal(ByteBuf buffer) {
		return null;
	}

	public boolean validateWake() {
		return true;
	}
}
