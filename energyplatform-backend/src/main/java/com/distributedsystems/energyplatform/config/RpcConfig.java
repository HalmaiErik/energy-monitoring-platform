package com.distributedsystems.energyplatform.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcConfig {

    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcService() {
        return new AutoJsonRpcServiceImplExporter();
    }
}
