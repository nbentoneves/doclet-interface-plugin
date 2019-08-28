package com.docletinterface.plugin;

import com.docletinterface.ui.DocletInterfaceHelperApplication;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;

import java.io.File;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DocletInterfacePluginTest extends AbstractMojoTestCase {

    @Mock
    private DocletInterfaceHelperApplication docletInterfaceHelperApplication;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        initMocks(this);
    }

    public void testPluginWithoutConfigs() throws Exception {

        String[] emptyConfig = new String[]{};
        doNothing().when(docletInterfaceHelperApplication).runService(eq(emptyConfig));
        DocletInterfacePlugin docletInterfacePlugin = (DocletInterfacePlugin) lookupMojo("genDoc",
                new File(getBasedir(), "src/test/resources/basic-test-plugin-empty-config.xml"));

        assertNotNull(docletInterfacePlugin);

        docletInterfacePlugin.setTimeout(1);
        docletInterfacePlugin.setDocletInterfaceHelperApplication(docletInterfaceHelperApplication);
        docletInterfacePlugin.execute();

        verify(docletInterfaceHelperApplication, new Times(1)).runService(eq(emptyConfig));

    }

    public void testPluginWithConfigs() throws Exception {

        String[] config = new String[]{"--sourceInterface=test"};

        doNothing().when(docletInterfaceHelperApplication).runService(eq(config));
        DocletInterfacePlugin docletInterfacePlugin = (DocletInterfacePlugin) lookupMojo("genDoc",
                new File(getBasedir(), "src/test/resources/basic-test-plugin-config.xml"));

        assertNotNull(docletInterfacePlugin);

        docletInterfacePlugin.setTimeout(1);
        docletInterfacePlugin.setDocletInterfaceHelperApplication(docletInterfaceHelperApplication);
        docletInterfacePlugin.execute();

        verify(docletInterfaceHelperApplication, new Times(1)).runService(eq(config));


    }

}
