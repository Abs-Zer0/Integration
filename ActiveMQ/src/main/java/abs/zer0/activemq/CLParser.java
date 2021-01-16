/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activemq;

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
    private String firstOutputName = null;
    private String secondOutputName = null;

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
        final Option firstOutput = generate("f", "first", "The name of first output queue");
        opts.addOption(firstOutput);
        final Option secondOutput = generate("s", "second", "The name of second output queue");
        opts.addOption(secondOutput);

        this.parsedArgs = parser.parse(opts, commandLineArgs);
    }

    /**
     * Url ActiveMQ server
     *
     * @return server url or empty String if option not setted
     */
    public String getUrl() {
        if (this.url == null) {
            this.url = this.parsedArgs.getOptionValue("url", "");
        }

        return this.url;
    }

    /**
     * The name of input queue
     *
     * @return name or empty String if option not setted
     */
    public String getInputQueueName() {
        if (this.inputName == null) {
            this.inputName = this.parsedArgs.getOptionValue("input", "");
        }

        return this.inputName;
    }

    /**
     * The name of first output queue
     *
     * @return name or empty String if option not setted
     */
    public String getFirstOutputQueueName() {
        if (this.firstOutputName == null) {
            this.firstOutputName = this.parsedArgs.getOptionValue("first", "");
        }

        return this.firstOutputName;
    }

    /**
     * The name of second output queue
     *
     * @return name or empty String if option not setted
     */
    public String getSecondOutputQueueName() {
        if (this.secondOutputName == null) {
            this.secondOutputName = this.parsedArgs.getOptionValue("second", "");
        }

        return this.secondOutputName;
    }

    private Option generate(String shortName, String longName, String description) {
        return Option.builder(shortName)
                .longOpt(longName)
                .required()
                .numberOfArgs(1)
                .valueSeparator(' ')
                .desc(description)
                .build();
    }
}
