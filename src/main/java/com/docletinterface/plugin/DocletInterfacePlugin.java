package com.docletinterface.plugin;

import com.docletinterface.ui.DocletInterfaceHelperApplication;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "genDoc")
public class DocletInterfacePlugin extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocletInterfacePlugin.class);

    private static final String PROPERTY_SOURCE_NAME = "--sourceInterface=";

    private long timeout = 0;

    private DocletInterfaceHelperApplication docletInterfaceHelperApplication;

    @Parameter(property = "genDoc.sourceInterface")
    private String sourceInterface;

    public void execute() {

        synchronized (this) {

            LOGGER.info("Starting DocletInterfacePlugin...");

            List<String> configs = new ArrayList<>();

            if (sourceInterface != null && sourceInterface.length() != 0) {
                configs.add(PROPERTY_SOURCE_NAME + this.sourceInterface);
            }

            LOGGER.info("Configs set: {}", configs);

            if (this.docletInterfaceHelperApplication == null) {
                setDocletInterfaceHelperApplication(new DocletInterfaceHelperApplication());
            }

            LOGGER.info("Starting run the DocletInterfaceHelper...when you need to finish you should kill the process!");
            this.docletInterfaceHelperApplication.runService(configs.toArray(new String[configs.size()]));

            try {
                this.wait(timeout);
            } catch (InterruptedException ex) {
                LOGGER.error("DocletInterfacePlugin was interrupted...", ex);
                Thread.currentThread().interrupt();
            }
        }

    }

    void setDocletInterfaceHelperApplication(DocletInterfaceHelperApplication docletInterfaceHelperApplication) {
        this.docletInterfaceHelperApplication = docletInterfaceHelperApplication;
    }

    void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
