package org.tsystems.javaschool.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Trofim Kremen
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
@RequiredArgsConstructor
public class JMSConfig {

    private final Environment environment;

    @Bean
    public Context jndiContext() throws NamingException {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        properties.setProperty(Context.PROVIDER_URL, environment.getProperty("provider.url"));
        properties.setProperty(Context.SECURITY_PRINCIPAL, environment. getProperty("security.principal"));
        properties.setProperty(Context.SECURITY_CREDENTIALS, environment.getProperty("security.password"));
        return new InitialContext(properties);
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory() throws NamingException {
        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext().lookup("jms/RemoteConnectionFactory");
        UserCredentialsConnectionFactoryAdapter factoryAdapter = new UserCredentialsConnectionFactoryAdapter();
        factoryAdapter.setUsername(Objects.requireNonNull(environment.getProperty("security.principal")));
        factoryAdapter.setPassword(Objects.requireNonNull(environment.getProperty("security.password")));
        factoryAdapter.setTargetConnectionFactory(connectionFactory);
        return factoryAdapter;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws NamingException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(jmsConnectionFactory());
        template.setPubSubDomain(true);
        template.setMessageConverter(jmsMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jmsMessageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public Topic standTopicName() throws NamingException {
        return (Topic) jndiContext().lookup("jms/topic/TimetableTopic");
    }

}
