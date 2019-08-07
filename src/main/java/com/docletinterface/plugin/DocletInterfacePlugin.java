package com.docletinterface.plugin;

import com.docletinterface.ui.DocletInterfaceHelperApplication;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "genDoc")
public class DocletInterfacePlugin extends AbstractMojo {

    private static final String PROPERTY_SOURCE_NAME = "--sourceInterface=";

    private DocletInterfaceHelperApplication docletInterfaceHelperApplication;

    @Parameter(property = "genDoc.sourceInterface")
    private String sourceInterface;

    public void execute() {

        synchronized (this) {
            List<String> configs = new ArrayList<>();

            System.out.println("RUNNING THREAD!!!!");

            if (sourceInterface != null && sourceInterface.length() != 0) {
                configs.add(PROPERTY_SOURCE_NAME + this.sourceInterface);
            }

            if (this.docletInterfaceHelperApplication == null) {
                setDocletInterfaceHelperApplication(new DocletInterfaceHelperApplication());
            }

            this.docletInterfaceHelperApplication.runService(configs.toArray(new String[configs.size()]));

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    protected void setDocletInterfaceHelperApplication(DocletInterfaceHelperApplication docletInterfaceHelperApplication) {
        this.docletInterfaceHelperApplication = docletInterfaceHelperApplication;
    }

}
