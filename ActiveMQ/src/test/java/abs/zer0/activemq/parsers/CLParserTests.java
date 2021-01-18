/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activemq.parsers;

import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class CLParserTests {

    @Test
    public void withoutOptions() throws ParseException {
        String[] args = new String[0];

        CLParser parser = new CLParser(args);

        Assert.assertEquals("tcp://localhost:61616", parser.getUrl());
        Assert.assertEquals("Input", parser.getInputQueueName());
        Assert.assertEquals("FirstOutput", parser.getFirstOutputQueueName());
        Assert.assertEquals("SecondOutput", parser.getSecondOutputQueueName());
    }

    @Test
    public void withoutArgs() throws ParseException {
        String[] args = new String[]{
            "-u", "", "-i", "", "-f", "", "-s", ""
        };

        CLParser parser = new CLParser(args);

        Assert.assertEquals("", parser.getUrl());
        Assert.assertEquals("", parser.getInputQueueName());
        Assert.assertEquals("", parser.getFirstOutputQueueName());
        Assert.assertEquals("", parser.getSecondOutputQueueName());
    }

    @Test
    public void argsNotNull() throws ParseException {
        String[] args = new String[0];

        CLParser parser = new CLParser(args);

        Assert.assertNotNull(parser.getUrl());
        Assert.assertNotNull(parser.getInputQueueName());
        Assert.assertNotNull(parser.getFirstOutputQueueName());
        Assert.assertNotNull(parser.getSecondOutputQueueName());
    }

    @Test
    public void allArgs() throws ParseException {
        String[] args = new String[]{
            "-u", "tcp://127.0.0.1", "-i", "queue", "-f", "queue", "-s", "queue"
        };

        CLParser parser = new CLParser(args);

        Assert.assertEquals("tcp://127.0.0.1", parser.getUrl());
        Assert.assertEquals("queue", parser.getInputQueueName());
        Assert.assertEquals("queue", parser.getFirstOutputQueueName());
        Assert.assertEquals("queue", parser.getSecondOutputQueueName());
    }

    @Test
    public void randomOrder() throws ParseException {
        String[] args = new String[]{
            "-i", "queue", "-s", "queue", "-u", "tcp://127.0.0.1", "-f", "queue"
        };

        CLParser parser = new CLParser(args);

        Assert.assertEquals("tcp://127.0.0.1", parser.getUrl());
        Assert.assertEquals("queue", parser.getInputQueueName());
        Assert.assertEquals("queue", parser.getFirstOutputQueueName());
        Assert.assertEquals("queue", parser.getSecondOutputQueueName());
    }
}
