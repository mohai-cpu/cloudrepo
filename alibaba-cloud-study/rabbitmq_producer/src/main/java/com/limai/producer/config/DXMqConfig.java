/*
package com.limai.producer.config;

import lombok.Data;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

*/
/**
 * @author Jiangmh
 * @do
 * @date 2020/07/05
 **//*

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class DXMqConfig {
    private String addresses;
    private String username;
    private String password;
    private String virtualHost;

    @Primary
    @Bean(name = "dxRabbitmqFactory")
    public ConnectionFactory getDXConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(this.getAddresses());
        factory.setUsername(this.getUsername());
        factory.setPassword(this.getPassword());
        factory.setVirtualHost(this.getVirtualHost());
        return factory;
    }

    @Bean(name = "dxRabbitTemplate")
    public RabbitTemplate getDxRabbitTemplate(@Qualifier("dxRabbitmqFactory") ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }

    @Bean(name = "dxRabbitListenerFactory")
    public SimpleRabbitListenerContainerFactory getDXSimpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, @Qualifier("dxRabbitmqFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory listenerFactory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(listenerFactory, connectionFactory);
        return listenerFactory;
    }
}
*/
