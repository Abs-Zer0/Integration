/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activemq.processors;

import abs.zer0.utils.DoublePrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class DelimiterTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void incorrectUrl() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);

        try (Delimiter delimiter = new Delimiter("", "i", "f", "s")) {

        } catch (JMSException ex) {
            Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        thrown = ExpectedException.none();
    }

    @Test
    public void cannotConnect() throws JMSException {
        thrown.expect(JMSException.class);

        try (Delimiter delimiter = new Delimiter("tcp://1.1.1.1:61616", "i", "f", "s")) {
            Thread.sleep(500);
        } catch (InterruptedException | IllegalArgumentException ex) {
            Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        thrown = ExpectedException.none();
    }

    @Test
    public void inputSameOneOfOutput() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);

        try (Delimiter delimiter = new Delimiter("tcp://localhost:61616", "same", "same", "s")) {

        } catch (JMSException ex) {
            Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        thrown = ExpectedException.none();

        thrown.expect(IllegalArgumentException.class);

        try (Delimiter delimiter = new Delimiter("tcp://localhost:61616", "same", "f", "same")) {

        } catch (JMSException ex) {
            Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        thrown = ExpectedException.none();
    }

    @Test
    public void outputsSame() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);

        try (Delimiter delimiter = new Delimiter("tcp://localhost:61616", "i", "same", "same")) {

        } catch (JMSException ex) {
            Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        thrown = ExpectedException.none();
    }

    @Test
    public void correctWork() {
        String url = "tcp://localhost:61616";
        String input = "Input";
        String output1 = "Output1";
        String output2 = "Output2";
        String message = "This is message";

        StringBuilder out1 = new StringBuilder();
        StringBuilder out2 = new StringBuilder();

        try (Delimiter delimiter = new Delimiter(url, input, output1, output2)) {
            Session session = delimiter.getSession();

            MessageProducer sender = session.createProducer(session.createQueue(input));
            sender.send(session.createTextMessage(message));
            sender.close();

            Thread.sleep(1000);

            MessageConsumer firstListener = session.createConsumer(session.createQueue(output1));
            MessageConsumer secondListener = session.createConsumer(session.createQueue(output2));

            firstListener.setMessageListener((msg) -> {
                try {
                    out1.append(((TextMessage) msg).getText());
                } catch (JMSException ex) {
                    Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            secondListener.setMessageListener((msg) -> {
                try {
                    out2.append(((TextMessage) msg).getText());
                } catch (JMSException ex) {
                    Logger.getLogger(DelimiterTests.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (JMSException | InterruptedException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }

        Assert.assertEquals(message, out1.toString());
        Assert.assertEquals(message, out2.toString());
    }

    @Test(timeout = 5000)
    public void emptyMessage() {
        String url = "tcp://localhost:61616";
        String input = "Input";
        String output1 = "Output1";
        String output2 = "Output2";
        String message = "";

        StringBuilder consoleLog = new StringBuilder();

        try (Delimiter delimiter = new Delimiter(url, input, output1, output2)) {
            File tmp = File.createTempFile("tmp", "tmp");
            tmp.createNewFile();
            PrintStream fileOut = new PrintStream(tmp);
            PrintStream last = System.out;
            System.setOut(new DoublePrintStream(last, fileOut));

            Session session = delimiter.getSession();

            MessageProducer sender = session.createProducer(session.createQueue(input));
            sender.send(session.createTextMessage(message));
            sender.close();

            Thread.sleep(1000);

            fileOut.close();
            System.setOut(last);
            BufferedReader fileIn = new BufferedReader(new FileReader(tmp));

            consoleLog.append(fileIn.readLine());

            fileIn.close();
            tmp.delete();
        } catch (JMSException | InterruptedException | IOException ex) {
            Assert.fail(ex.getLocalizedMessage());
        }

        Assert.assertEquals("The message is empty", consoleLog.toString());
    }
}
