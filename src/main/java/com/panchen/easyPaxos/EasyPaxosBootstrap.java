package com.panchen.easyPaxos;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Description:
 * @author: chenp
 * @date: 2019/08/15  4:44:51
 */
@SpringBootApplication
public class EasyPaxosBootstrap {

    public static void main(String[] args) {
        // ignore web
        new SpringApplicationBuilder(EasyPaxosBootstrap.class).web(WebApplicationType.NONE)
            .run(args);
        // loop
        while (Boolean.TRUE) {

        }
    }
}
