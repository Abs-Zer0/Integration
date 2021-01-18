/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel;

import abs.zer0.activecamel.routes.JmsRoute;
import java.util.Base64;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.component.jms.JmsQueueEndpoint;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class ContextTests extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new JmsRoute("Input", "Output", Base64.getDecoder());
    }

    @Override
    protected CamelContext createCamelContext() {
        final CamelContext camel = new DefaultCamelContext();

        final ConnectionFactory connectionFactory
                = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        return camel;
    }

    @Test
    public void decodeMessage() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:decodedMsg");
        mock.expectedMessageCount(1);

        String message = "The message";
        mock.expectedBodiesReceived(message);

        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
        template.sendBody("direct:start", encodedMessage);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void emptyMessage() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:emptyMsg");
        mock.expectedMessageCount(1);

        String message = "";
        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());
        template.sendBody("direct:start", encodedMessage);

        assertMockEndpointsSatisfied();
    }
}
