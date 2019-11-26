package com.panchen.easyPaxos.core;


import javax.annotation.PostConstruct;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Log4j2
@Configuration
public class Paxos {

    @Autowired
    private Cluster cluster;

    @Value("#{easyPaxos.ip}")
    private String ip;

    @Value("#{easyPaxos.port}")
    private int port;

    @Value("#{easyPaxos.cluster.nodes}")
    private String[] nodeIP;

    @PostConstruct
    public void init() {
        log.info(" hello  !   easyPaxos ! ");
        configure();
        initCluster();
    }

    private void initCluster() {
        cluster.select();
    }

    public boolean proposal(String key, String value) {
        return cluster.proposal(key, value);
    }

    private void configure() {
        if (null == ip || "".equals(ip) || 0 >= port) {
            throw new RuntimeException(" check your conf ! ");
        }


    }


}
