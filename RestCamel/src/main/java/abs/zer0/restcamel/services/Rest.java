/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abs.zer0.restcamel.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Абс0лютный Н0ль
 */
public class Rest {

    public String stat() {
        return getImplVendor() + "-" + getImplVersion();
    }

    private String getImplVendor() {
        final String vendor = getClass().getPackage().getImplementationVendor();

        if (vendor == null) {
            return loadFromManifest("Implementation-Vendor", "RestCamel");
        } else {
            return vendor;
        }
    }

    private String getImplVersion() {
        final String version = getClass().getPackage().getImplementationVersion();

        if (version == null) {
            return loadFromManifest("Implementation-Version", "1.0-SNAPSHOT");
        } else {
            return version;
        }
    }

    private String loadFromManifest(String propName, String defaultValue) {
        try {
            final Properties props = new Properties();

            final InputStream manifest = getClass().getClassLoader().getResourceAsStream("/META-INF/MANIFEST.MF");
            if (manifest == null) {
                return defaultValue;
            }

            props.load(manifest);

            return props.getProperty(propName);
        } catch (IOException ex) {
            Logger.getLogger(Rest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getLocalizedMessage());

            return defaultValue;
        }
    }
}
