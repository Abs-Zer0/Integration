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
public class ConsoleOutputProcessor implements Processor {

    /**
     * Processor that output message body to console
     */
    public ConsoleOutputProcessor() {

    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final String msgBody = exchange.getIn().getBody(String.class);

        System.out.println(msgBody);
    }

}
