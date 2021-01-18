/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.restcamel;

import abs.zer0.restcamel.routes.RestRoute;
import abs.zer0.restcamel.services.Rest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.jndi.JndiContext;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class App {

    public static void main(String[] args) {
        final CamelContext camel = new DefaultCamelContext();

        try {
            final RestRoute restRoute = new RestRoute();
            camel.addRoutes(restRoute);

            camel.start();

            System.out.println("Press 'e' to exit the program");
            while (true) {
                int symbol = System.in.read();
                if (symbol == (int) 'e') {
                    break;
                }
            }

            camel.stop();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());

            camel.stop();
        }
    }
}
