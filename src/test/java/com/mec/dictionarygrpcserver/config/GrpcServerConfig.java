package com.mec.dictionarygrpcserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.test.context.support.TestPropertySourceUtils.addInlinedPropertiesToEnvironment;
import static org.springframework.test.util.TestSocketUtils.findAvailableTcpPort;

@Configuration
public class GrpcServerConfig {
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Bean
    public int grpcPort() {
        int port = findAvailableTcpPort();
        addInlinedPropertiesToEnvironment(applicationContext, "grpc.port=" + port);
        return port;
    }
}
