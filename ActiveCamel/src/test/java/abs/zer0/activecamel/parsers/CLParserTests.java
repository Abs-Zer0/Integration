/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.parsers;

import java.util.Base64;
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
        Assert.assertEquals("Input", parser.getInputQueue());
        Assert.assertEquals("Output", parser.getOutputQueue());
        Assert.assertEquals(Base64.getDecoder(), parser.getBase64Decoder());
    }

    @Test
    public void withoutArgs() throws ParseException {
        String[] args = new String[]{
            "-u", "", "-i", "", "-o", "", "-t", ""
        };

        CLParser parser = new CLParser(args);

        Assert.assertEquals("", parser.getUrl());
        Assert.assertEquals("", parser.getInputQueue());
        Assert.assertEquals("", parser.getOutputQueue());
        Assert.assertEquals(Base64.getDecoder(), parser.getBase64Decoder());
    }

    @Test
    public void argsNotNull() throws ParseException {
        String[] args = new String[0];

        CLParser parser = new CLParser(args);

        Assert.assertNotNull(parser.getUrl());
        Assert.assertNotNull(parser.getInputQueue());
        Assert.assertNotNull(parser.getOutputQueue());
        Assert.assertNotNull(parser.getBase64Decoder());
    }

    @Test
    public void allArgs() throws ParseException {
        String[] args = new String[]{
            "-u", "tcp://127.0.0.1", "-i", "queue", "-o", "queue", "-t", "mime"
        };

        CLParser parser = new CLParser(args);

        Assert.assertEquals("tcp://127.0.0.1", parser.getUrl());
        Assert.assertEquals("queue", parser.getInputQueue());
        Assert.assertEquals("queue", parser.getOutputQueue());
        Assert.assertEquals(Base64.getMimeDecoder(), parser.getBase64Decoder());
    }

    @Test
    public void randomOrder() throws ParseException {
        String[] args = new String[]{
            "-i", "queue", "-t", "url", "-u", "tcp://127.0.0.1", "-o", "queue"
        };

        CLParser parser = new CLParser(args);

        Assert.assertEquals("tcp://127.0.0.1", parser.getUrl());
        Assert.assertEquals("queue", parser.getInputQueue());
        Assert.assertEquals("queue", parser.getOutputQueue());
        Assert.assertEquals(Base64.getUrlDecoder(), parser.getBase64Decoder());
    }
}
