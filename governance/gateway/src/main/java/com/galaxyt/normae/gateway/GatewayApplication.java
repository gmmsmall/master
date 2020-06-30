package com.galaxyt.normae.gateway;

import com.galaxyt.normae.gateway.security.AuthorityPull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GatewayApplication implements CommandLineRunner {


    @Autowired
    private AuthorityPull authorityPull;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("============================ Gateway * System startup completed ===================================================");
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorityPull.initialize();
    }
}
