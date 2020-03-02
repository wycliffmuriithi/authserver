package com.wyki.idsauth.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * Class name: MessagebrokerConfig
 * Creater: wgicheru
 * Date:6/7/2019
 */
@Configuration
public class MessagebrokerConfig implements RabbitListenerConfigurer {
    @Bean
    public TopicExchange getAppExchange() {
        return new TopicExchange("amq.topic");
    }

    @Bean
    public Queue getCreateUserQueue() {
        return new Queue("createuser");
    }

    @Bean
    public Queue getCreateResourceQueue() {
        return new Queue("createresource");
    }

    @Bean
    public Queue getUpdateuserPasswordQueue() {
        return new Queue("updatepassword");
    }

    @Bean
    public Queue getDeactivateuserQueue() {
        return new Queue("deactivateuser");
    }


    @Bean
    public Binding declareUserBiding() {
        return BindingBuilder.bind(getCreateUserQueue()).to(getAppExchange()).with("auth.createuser");
    }

    @Bean
    public Binding declareResourceBiding() {
        return BindingBuilder.bind(getCreateResourceQueue()).to(getAppExchange()).with("auth.createresource");
    }

    @Bean
    public Binding declarePasswordBiding() {
        return BindingBuilder.bind(getUpdateuserPasswordQueue()).to(getAppExchange()).with("auth.updatepassword");
    }

    @Bean
    public Binding declareDeactivateuserBiding() {
        return BindingBuilder.bind(getDeactivateuserQueue()).to(getAppExchange()).with("auth.deactivateuser");
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /* Bean for rabbitTemplate */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
