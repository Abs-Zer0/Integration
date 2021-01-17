/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class EmptyMessageProcessor implements Processor {

    /**
     * Processor that exchange message to "The message is empty" for console
     * output
     */
    public EmptyMessageProcessor() {

    }

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("The message is empty", String.class);
    }

}
