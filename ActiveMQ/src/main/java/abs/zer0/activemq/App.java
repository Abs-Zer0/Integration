/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activemq;

import abs.zer0.activemq.processors.Delimiter;
import abs.zer0.activemq.parsers.CLParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class App {

    public static void main(String[] args) {
        try {
            final CLParser parser = new CLParser(args);
            
            final String url = parser.getUrl();
            final String input = parser.getInputQueueName();
            final String firstOutput = parser.getFirstOutputQueueName();
            final String secondOutput = parser.getSecondOutputQueueName();

            final Delimiter delimiter = new Delimiter(url, input, firstOutput, secondOutput);

            System.out.println("Press 'e' to exit the program");
            while (true) {
                int symbol = System.in.read();
                if (symbol == (int) 'e') {
                    break;
                }
            }

            delimiter.close();
        } catch (ParseException | IOException | IllegalArgumentException | JMSException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
