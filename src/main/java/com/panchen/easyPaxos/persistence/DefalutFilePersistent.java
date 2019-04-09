package com.panchen.easyPaxos.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.panchen.easyPaxos.core.PaxosMessage;

@Configuration
public class DefalutFilePersistent implements FilePersistent {

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@Value("#{easyPaxos.log}")
	private String log;

	private static FileChannel fileChannel;
	private volatile int offset;

	@SuppressWarnings("resource")
	@Override
	public boolean serialize(PaxosMessage paxosMessage) {
		lock.writeLock().lock();
		if (null == fileChannel) {
			RandomAccessFile randomAccessFile = null;
			try {
				randomAccessFile = new RandomAccessFile(log, "rw");
			} catch (FileNotFoundException e) {
				return false;
			}
			fileChannel = randomAccessFile.getChannel();
		}
		ByteBuffer buf = ByteBuffer.allocate(paxosMessage.capacity());
		buf.flip();
		while (buf.hasRemaining()) {
			try {
				fileChannel.write(buf);
			} catch (IOException e) {
				return false;
			}
		}
		offset = paxosMessage.capacity();
		lock.writeLock().unlock();
		return true;

	}

	@Override
	public byte[] deserialize() {
		lock.readLock().lock();
		if (null == fileChannel) {
			return null;
		}
		ByteBuffer buf = ByteBuffer.allocate(offset);
		int bytesRead = 0;
		try {
			bytesRead = fileChannel.read(buf);
		} catch (IOException e) {
			return null;
		}
		if (bytesRead != offset) {
			return buf.array();
		}
		return null;
	}

	@Override
	public void persistent(PaxosMessage paxosMessage) {
		// TODO Auto-generated method stub
		
	}

}
