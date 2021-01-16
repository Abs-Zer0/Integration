/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.activemq;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class Delimiter implements AutoCloseable {

    private String inputName;
    private String firstOutputName;
    private String secondOutputName;
    private Connection connection;
    private Session session;
    private MessageConsumer inputListener;

    /**
     * Process that get message from one queue and send copies to another two
     * queues
     *
     * @param url ActiveMQ server url
     * @param input the name of input queue
     * @param firstOutput the name of first output queue
     * @param secondOutput the name of second output queue
     * @throws IllegalArgumentException wrong names of queues
     * @throws JMSException
     */
    public Delimiter(String url, String input, String firstOutput, String secondOutput) throws IllegalArgumentException, JMSException {
        if (input.equals(firstOutput) || input.equals(secondOutput)) {
            throw new IllegalArgumentException("The name of input queue cannot be equals the name of output queue");
        }
        if (firstOutput.equals(secondOutput)) {
            throw new IllegalArgumentException("The names of output queues cannot be equals");
        }

        this.inputName = input.equals("") ? "Input" : input;
        this.firstOutputName = firstOutput.equals("") ? "FirstOutput" : firstOutput;
        this.secondOutputName = secondOutput.equals("") ? "SecondOutput" : secondOutput;

        initConnection(url);
        initMessageListener();
    }

    /**
     * Close all sessions with ActiveMQ server
     *
     * @throws JMSException
     */
    @Override
    public void close() throws JMSException {
        this.inputListener.close();
        this.session.close();
        this.connection.close();
    }

    /**
     * Connect with ActiveMQ server and initialize session
     *
     * @param url ActiveMQ server url
     * @throws JMSException
     */
    private void initConnection(String url) throws JMSException {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("failover://" + url);
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");

        this.connection = connectionFactory.createConnection();
        this.connection.start();

        this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    /**
     * Create message listener for input queue
     *
     * @throws JMSException
     */
    private void initMessageListener() throws JMSException {
        this.inputListener = this.session.createConsumer(this.session.createQueue(this.inputName));
        this.inputListener.setMessageListener((msg) -> {
            try {
                final TextMessage textMsg = (TextMessage) msg;
                if (textMsg.getText().isEmpty()) {
                    System.out.println("The message is empty");
                }

                final Thread firstSender = new Thread(() -> {
                    try {
                        sendClonedMessageTo(textMsg, this.firstOutputName);
                    } catch (JMSException ex) {
                        Logger.getLogger(Delimiter.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex.getLocalizedMessage());
                    }
                });
                final Thread secondSender = new Thread(() -> {
                    try {
                        sendClonedMessageTo(textMsg, this.secondOutputName);
                    } catch (JMSException ex) {
                        Logger.getLogger(Delimiter.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(ex.getLocalizedMessage());
                    }
                });

                firstSender.join();
                secondSender.join();
            } catch (JMSException | ClassCastException | InterruptedException ex) {
                Logger.getLogger(Delimiter.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getLocalizedMessage());
            }
        });
    }

    /**
     * Send "msg" message to queue with "queueName" name
     *
     * @param msg message that should send
     * @param queueName the name of destination queue
     * @throws JMSException
     */
    private void sendClonedMessageTo(TextMessage msg, String queueName) throws JMSException {
        final MessageProducer producer = this.session.createProducer(this.session.createQueue(queueName));
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        final TextMessage textMsg = this.session.createTextMessage(msg.getText());
        producer.send(textMsg);

        producer.close();
    }
}
