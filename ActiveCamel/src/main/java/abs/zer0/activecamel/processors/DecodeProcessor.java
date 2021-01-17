/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.processors;

import java.util.Base64.Decoder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class DecodeProcessor implements Processor {

    private Decoder base64;

    /**
     * Processor that decode base64 message
     *
     * @param base64Decoder base64 decoder
     */
    public DecodeProcessor(Decoder base64Decoder) {
        this.base64 = base64Decoder;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final String msgBody = exchange.getIn().getBody(String.class);
        final String decodedMsgBody = new String(this.base64.decode(msgBody));

        exchange.getIn().setBody(decodedMsgBody, String.class);
    }

}
