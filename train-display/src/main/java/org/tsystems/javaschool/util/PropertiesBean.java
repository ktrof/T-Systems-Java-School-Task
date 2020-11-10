package org.tsystems.javaschool.util;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Trofim Kremen
 */
@Singleton
@Slf4j
public class PropertiesBean {

    private Properties properties;

    @PostConstruct
    public void init(){
        try {
            loadProperties();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void loadProperties() throws IOException {
        properties = new Properties();
        String propFileName = "application.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            properties.load(inputStream);
            log.info("properties are loaded");
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public String getBrokerUrl() {
        return properties.getProperty("broker.url.client");
    }

    public String getDestinationName() {
        return properties.getProperty("queue.name.client");
    }

}
