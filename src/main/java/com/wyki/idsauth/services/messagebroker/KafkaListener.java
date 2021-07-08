package com.wyki.idsauth.services.messagebroker;


import com.wyki.idsauth.wrappers.MessageBrokerUserCreation;
import com.wyki.idsauth.wrappers.UserDetailsWrapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener extends RouteBuilder {

    public  void configure() throws Exception {
//        buildRequestProcessor = new BuildRequestProcessor();
        from("kafka:{{kafka.createsdpuser.topic.name}}?brokers={{kafka.server.ip}}:{{kafka.server.port}}&groupId={{kafka.users.group.id}}")
                .routeId("createsdpuser-route")
                .log("Message received from Kafka : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, MessageBrokerUserCreation.class)
                .to("bean-validator:myvalidatorname")
                .bean(MessageListener.class,"receiveUsercreationRequest");
//                .to("direct:sendpostrequest");

        from("kafka:{{kafka.updatepassword.topic.name}}?brokers={{kafka.server.ip}}:{{kafka.server.port}}&groupId={{kafka.users.group.id}}")
                .routeId("updatepassword-route")
                .log("Message received from Kafka : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, UpdateuserPassword.class)
                .to("bean-validator:myvalidatorname")
                .bean(MessageListener.class,"receiveUpdatePassword");

        from("kafka:{{kafka.deactivateuser.topic.name}}?brokers={{kafka.server.ip}}:{{kafka.server.port}}&groupId={{kafka.users.group.id}}")
                .routeId("deactivateuser-route")
                .log("Message received from Kafka : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, UserDetailsWrapper.class)
                .to("bean-validator:myvalidatorname")
                .bean(MessageListener.class,"deactivateuserAccount");

        from("kafka:{{kafka.updateuserdetails.topic.name}}?brokers={{kafka.server.ip}}:{{kafka.server.port}}&groupId={{kafka.users.group.id}}")
                .routeId("updateuserdetails-route")
                .log("Message received from Kafka : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, UserDetailsWrapper.class)
                .to("bean-validator:myvalidatorname")
                .bean(MessageListener.class,"updateUserDetails");
    }

}
