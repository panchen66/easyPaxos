package com.panchen.easyPaxos.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.panchen.easyPaxos.future.PaxosFutrueTask;
import com.panchen.easyPaxos.transport.NettyTransport;
import java.util.concurrent.TimeUnit;

public abstract class Node {

    public static NodeState state = NodeState.WATING;

    public NettyTransport nettyTransport;
    public InetSocketAddress inetSocketAddress;

    protected ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
        1,
        10, TimeUnit.SECONDS,
        new SynchronousQueue<>());

    public static BlockingQueue<PaxosFutrueTask> proposerTaskQueue = new ArrayBlockingQueue<PaxosFutrueTask>(
        1024);

    public static BlockingQueue<PaxosFutrueTask> confirmTaskQueue = new ArrayBlockingQueue<PaxosFutrueTask>(
        1024);

}