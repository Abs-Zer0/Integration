/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.restcamel.services;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class RestTests {

    @Test
    public void statMethodTest() {
        Rest rest = new Rest();

        Assert.assertEquals("RestCamel-1.0-SNAPSHOT", rest.stat());
    }
}
