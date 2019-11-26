package com.panchen.easyPaxos.core;


import com.panchen.easyPaxos.transport.NettyTransport;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.panchen.easyPaxos.persistence.FilePersistent;

@Component
@Builder
public class Server extends Node {

    Acceptor acceptor;
    Proposer proposer;
    Learner learner;

    @Autowired
    FilePersistent filePersistent;

    @Autowired
    NettyTransport nettyTransport;


}
