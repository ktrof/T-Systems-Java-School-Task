package org.tsystems.javaschool.config;

import lombok.RequiredArgsConstructor;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author Trofim Kremen
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
@RequiredArgsConstructor
public class JMSConfig {

    private final Environment environment;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(environment.getProperty("broker.url"));
        System.out.println(environment.getProperty("broker.url"));
        connectionFactory.setUserName(environment.getProperty("broker.username"));
        connectionFactory.setPassword(environment.getProperty("broker.password"));
        return connectionFactory;
    }

}
