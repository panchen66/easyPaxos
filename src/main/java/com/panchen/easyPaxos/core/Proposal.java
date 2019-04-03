package com.panchen.easyPaxos.core;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;

import io.netty.buffer.ByteBuf;

public class Proposal {

	private Long version;
	private byte[] content;

	public Proposal(ProposalHead proposalHead, String key, String value) {
		this.content = Ints.toByteArray(proposalHead.getValue());
		this.content = Bytes.concat(content, key.getBytes(), value.getBytes());
		this.version = System.currentTimeMillis();
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
