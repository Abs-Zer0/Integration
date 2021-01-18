/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.restcamel.routes;

import abs.zer0.restcamel.services.Rest;
import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class RestRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("netty-http:http://0.0.0.0:8080/stat").bean(Rest.class, "stat");
    }

}
