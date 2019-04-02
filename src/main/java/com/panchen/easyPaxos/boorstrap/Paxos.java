package com.panchen.easyPaxos.boorstrap;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Splitter;
import com.panchen.easyPaxos.core.Client;
import com.panchen.easyPaxos.core.Cluster;
import com.panchen.easyPaxos.core.Selector;

@Configuration
public class Paxos {

	@Autowired
	private Selector selector;

	@Value("#{easyPaxos.ip}")
	private String ip;

	@Value("#{easyPaxos.port}")
	private int port;

	@Value("#{easyPaxos.cluster.nodes}")
	private String[] nodeIP;

	private Logger logger = LoggerFactory.getLogger(Paxos.class);

	private Cluster cluster;

	@PostConstruct
	public void init() {
		logger.info(" hello easyPaxos ! ");

		ip2Node();
		initSelector();
		cluster.wake();
	}

	private void ip2Node() {
		if (null == ip || "".equals(ip) || 0 >= port) {
			throw new RuntimeException(" check your conf ! ");
		}
		Set<String> ipSet = new HashSet<String>();
		ipSet.add(ip + ":" + port);
		InetSocketAddress local = new InetSocketAddress(ip, port);
		Client localClient = new Client(local);
		cluster.joinCluster(localClient);
		for (String ip : nodeIP) {
			int size = ipSet.size();
			ipSet.add(ip);
			if (size < ipSet.size()) {
				List<String> address = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(ip);
				InetSocketAddress inetSocketAddress = new InetSocketAddress(address.get(0),
						Integer.valueOf(address.get(1)));
				Client client = new Client(inetSocketAddress);
				cluster.joinCluster(client);
			}

		}

	}

	private void initSelector() {
		cluster.selector = selector;
	}

}
