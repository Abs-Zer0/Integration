/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.processors;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class EmptyMessageProcessorTests {

    @Test
    public void messageConvert() {
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody("The message", String.class);

        try {
            EmptyMessageProcessor processor = new EmptyMessageProcessor();
            processor.process(exchange);
        } catch (Exception ex) {
            Logger.getLogger(EmptyMessageProcessorTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertEquals("The message is empty", exchange.getIn().getBody(String.class));
    }
}
