/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.processors;

import abs.zer0.utils.DoublePrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
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
public class ConsoleOutputProcessorTests {

    @Test
    public void correctOutput() {
        String message = "The message";

        StringBuilder consoleLog = new StringBuilder();

        try {
            File tmp = File.createTempFile("tmp", "tmp");
            PrintStream fileOut = new PrintStream(tmp);
            PrintStream last = System.out;
            System.setOut(new DoublePrintStream(last, fileOut));

            Exchange exchange = new DefaultExchange(new DefaultCamelContext());
            exchange.getIn().setBody(message, String.class);

            ConsoleOutputProcessor processor = new ConsoleOutputProcessor();
            processor.process(exchange);

            fileOut.close();
            System.setOut(last);
            BufferedReader fileIn = new BufferedReader(new FileReader(tmp));

            consoleLog.append(fileIn.readLine());

            fileIn.close();
            tmp.delete();
        } catch (Exception ex) {
            Assert.fail(ex.getLocalizedMessage());
        }

        Assert.assertEquals(message, consoleLog.toString());
    }
}
