package com.sesame.plugin;

import com.google.common.annotations.VisibleForTesting;
import com.sesame.ui.SesameJavaApplication;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "testing")
public class SesamePlugin extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SesamePlugin.class);

    private static final String PROPERTY_FILE_PATH = "config.file.path";
    private static final String PROPERTY_CONFIG_TYPE = "config.type";
    private static final String PROPERTY_CONFIG_FILE_PATH_APPLICATION_CONTEXT = "config.file.path.application.context";

    private long timeout = 0;

    private SesameJavaApplication sesameJavaApplication;

    @Parameter(property = "configFilePath")
    private String configFilePath;

    @Parameter(property = "configType", defaultValue = "TEXT")
    private String configType;

    @Parameter(property = "configFilePathApplicationContext")
    private String configFilePathAppContext;

    public void execute() {

        synchronized (this) {

            LOGGER.info("Sesame Application using maven plugin...");

            if (configFilePath != null && configFilePath.length() != 0) {
                System.setProperty(PROPERTY_FILE_PATH, this.configFilePath);
            }

            if (configType != null && configType.length() != 0) {
                System.setProperty(PROPERTY_CONFIG_TYPE, this.configType);
            }

            if (configFilePathAppContext != null && configFilePathAppContext.length() != 0) {
                System.setProperty(PROPERTY_CONFIG_FILE_PATH_APPLICATION_CONTEXT, this.configFilePathAppContext);
            }

            LOGGER.info("Configs set:");
            LOGGER.info("-> {} - {}", PROPERTY_FILE_PATH, System.getProperty(PROPERTY_FILE_PATH));
            LOGGER.info("-> {} - {}", PROPERTY_CONFIG_TYPE, System.getProperty(PROPERTY_CONFIG_TYPE));
            LOGGER.info("-> {} - {}", PROPERTY_CONFIG_FILE_PATH_APPLICATION_CONTEXT, System.getProperty(PROPERTY_CONFIG_FILE_PATH_APPLICATION_CONTEXT));

            if (this.sesameJavaApplication == null) {
                setSesameJavaApplication(new SesameJavaApplication());
            }

            LOGGER.info("Starting run the Sesame...when you need to finish you should kill the process!");
            this.sesameJavaApplication.runService(new String[]{});

            try {
                this.wait(timeout);
            } catch (InterruptedException ex) {
                LOGGER.error("Sesame Application was interrupted...", ex);
                Thread.currentThread().interrupt();
            }
        }

    }

    @VisibleForTesting
    void setSesameJavaApplication(SesameJavaApplication sesameJavaApplication) {
        this.sesameJavaApplication = sesameJavaApplication;
    }

    void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
