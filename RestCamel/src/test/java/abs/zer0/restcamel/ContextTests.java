/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.restcamel;

import abs.zer0.restcamel.routes.RestRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class ContextTests extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() {
        return new DefaultCamelContext();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RestRoute();
    }

    @Test
    public void getMethodTest() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:end");

        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived("RestCamel-1.0-SNAPSHOT");

        template.sendBody("direct:start", null);

        assertMockEndpointsSatisfied();
    }
}
