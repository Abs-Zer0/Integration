/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.routes;

import abs.zer0.activecamel.processors.ConsoleOutputProcessor;
import abs.zer0.activecamel.processors.EmptyMessageProcessor;
import abs.zer0.activecamel.processors.DecodeProcessor;
import java.util.Base64.Decoder;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class JmsRoute extends RouteBuilder {

    private String input;
    private String output;
    private Decoder base64;

    /**
     * Specialized for our task JMS RouteBuilder
     *
     * @param inputName the name of input queue
     * @param outputName the name of output queue
     * @param decoder base64 decoder
     */
    public JmsRoute(String inputName, String outputName, Decoder decoder) {
        if (inputName.equals(outputName)) {
            throw new IllegalArgumentException("The name of input queue cannot equals the name of output queue");
        }

        this.input = inputName;
        this.output = outputName;
        this.base64 = decoder;
    }

    @Override
    public void configure() throws Exception {
        final Processor emptyProcessor = new EmptyMessageProcessor();
        final Processor consoleProcessor = new ConsoleOutputProcessor();
        final Processor decodeProcessor = new DecodeProcessor(this.base64);

        from("jms:queue:" + this.input)
                .choice()
                .when(body().isEqualTo("")).process(emptyProcessor).process(consoleProcessor)
                .otherwise().process(decodeProcessor).to("jms:queue:" + this.output);
    }

}
