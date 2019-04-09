package com.panchen.easyPaxos.persistence;

import com.panchen.easyPaxos.core.PaxosMessage;

public interface FilePersistent {

	public boolean serialize(PaxosMessage paxosMessage);

	public byte[] deserialize();

	public void persistent(PaxosMessage paxosMessage);
}
