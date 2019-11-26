package com.panchen.easyPaxos.persistence;


import com.panchen.easyPaxos.core.PaxosMessage;
import java.nio.channels.FileChannel;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefalutFilePersistent implements FilePersistent {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Value("#{easyPaxos.log}")
    private String log;

    private static FileChannel fileChannel;
    private volatile int offset;


    @Override
    public boolean serialize(PaxosMessage paxosMessage) {
        return false;
    }

    @Override
    public byte[] deserialize() {
        return new byte[0];
    }

    @Override
    public void persistent(PaxosMessage paxosMessage) {

    }
}
