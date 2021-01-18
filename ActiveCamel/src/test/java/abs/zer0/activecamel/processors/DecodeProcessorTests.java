/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.processors;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
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
public class DecodeProcessorTests {

    @Test
    public void correctDecode() {
        String message = "The message";
        Encoder encoder = Base64.getEncoder();
        String encodedMessage = encoder.encodeToString(message.getBytes());

        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(encodedMessage, String.class);

        try {
            DecodeProcessor processor = new DecodeProcessor(Base64.getDecoder());
            processor.process(exchange);
        } catch (Exception ex) {
            Logger.getLogger(DecodeProcessorTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assert.assertEquals(message, exchange.getIn().getBody(String.class));
    }
}
