/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel;

import abs.zer0.activecamel.routes.JmsRoute;
import abs.zer0.activecamel.parsers.CLParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class App {

    public static void main(String[] args) {
        final CamelContext camel = new DefaultCamelContext();
        
        try {
            final CLParser parser = new CLParser(args);

            final ConnectionFactory connectionFactory
                    = new ActiveMQConnectionFactory("failover://" + parser.getUrl());
            camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

            final RouteBuilder jmsRoute
                    = new JmsRoute(parser.getInputQueue(), parser.getOutputQueue(), parser.getBase64Decoder());
            camel.addRoutes(jmsRoute);

            camel.start();

            System.out.println("Press 'e' to exit the program");
            while (true) {
                int symbol = System.in.read();
                if (symbol == (int) 'e') {
                    break;
                }
            }

            camel.stop();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
            camel.stop();
        }
    }
}
