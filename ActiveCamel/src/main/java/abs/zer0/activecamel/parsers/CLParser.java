/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activecamel.parsers;

import java.util.Base64;
import java.util.Base64.Decoder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class CLParser {

    private String url = null;
    private String inputName = null;
    private String outputName = null;
    private Decoder base64 = null;

    final private CommandLine parsedArgs;

    /**
     * Specialized for our task command line parser
     *
     * @param commandLineArgs arguments of command line
     * @throws ParseException if there are any problems encountered while
     * parsing the command line tokens
     */
    public CLParser(String[] commandLineArgs) throws ParseException {
        final CommandLineParser parser = new DefaultParser();

        final Options opts = new Options();
        final Option url = generate("u", "url", "Url ActiveMQ server");
        opts.addOption(url);
        final Option input = generate("i", "input", "The name of input queue");
        opts.addOption(input);
        final Option output = generate("o", "output", "The name of output queue");
        opts.addOption(output);
        final Option type = generate("t", "type", "The type of base64 decoder");
        opts.addOption(type);

        this.parsedArgs = parser.parse(opts, commandLineArgs);
    }

    /**
     * Url ActiveMQ server
     *
     * @return server url or "tcp://localhost:61616" if option not setted
     */
    public String getUrl() {
        if (this.url == null) {
            this.url = this.parsedArgs.getOptionValue("url", "tcp://localhost:61616");
        }

        return this.url;
    }

    /**
     * The name of input queue
     *
     * @return name or "Input" if option not setted
     */
    public String getInputQueue() {
        if (this.inputName == null) {
            this.inputName = this.parsedArgs.getOptionValue("input", "Input");
        }

        return this.inputName;
    }

    /**
     * The name of output queue
     *
     * @return name or "Output" if option not setted
     */
    public String getOutputQueue() {
        if (this.outputName == null) {
            this.outputName = this.parsedArgs.getOptionValue("output", "Output");
        }

        return this.outputName;
    }

    /**
     * The base64 decoder
     *
     * @return decoder certain typed or default decoder if option not setted
     */
    public Decoder getBase64Decoder() {
        if (this.base64 == null) {
            final String type = this.parsedArgs.getOptionValue("type", "default");

            switch (type) {
                case "url":
                    this.base64 = Base64.getUrlDecoder();
                    break;
                case "mime":
                    this.base64 = Base64.getMimeDecoder();
                    break;
                case "default":

                default:
                    this.base64 = Base64.getDecoder();
                    break;
            }
        }

        return this.base64;
    }

    private Option generate(String shortName, String longName, String description) {
        return Option.builder(shortName)
                .longOpt(longName)
                .numberOfArgs(1)
                .valueSeparator(' ')
                .desc(description)
                .build();
    }
}
