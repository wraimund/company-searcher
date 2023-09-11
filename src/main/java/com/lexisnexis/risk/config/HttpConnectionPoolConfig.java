package com.lexisnexis.risk.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConnectionPoolConfig {

    @Value("${http.connection.pool.maxTotalConnections:50}")
    int maxTotalConnections;

    @Value("${http.connection.pool.connectTimeout:30000}")
    int connectTimeout;

    @Value("${http.connection.pool.connectionRequestTimeout:30000}")
    int connectionRequestTimeout;

    @Value("${http.connection.pool.socketTimeout:60000}")
    int socketTimeout;

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(maxTotalConnections);
        return poolingConnectionManager;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingConnectionManager())
                .build();
    }
}
