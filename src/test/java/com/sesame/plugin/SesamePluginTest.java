package com.sesame.plugin;

import com.sesame.ui.SesameJavaApplication;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class SesamePluginTest extends AbstractMojoTestCase {

    @Mock
    private SesameJavaApplication sesameJavaApplication;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        initMocks(this);
    }

    public void testPluginWithoutConfigs() throws Exception {

        String[] emptyConfig = new String[]{};
        doNothing().when(sesameJavaApplication).runService(eq(emptyConfig));
        SesamePlugin sesamePlugin = (SesamePlugin) lookupMojo("testing",
                new File(getBasedir(), "src/test/resources/basic-test-plugin-empty-config.xml"));

        assertNotNull(sesamePlugin);

        sesamePlugin.setTimeout(1);
        sesamePlugin.setSesameJavaApplication(sesameJavaApplication);
        sesamePlugin.execute();

        verify(sesameJavaApplication, new Times(1)).runService(eq(emptyConfig));

    }

    public void testPluginWithConfigs() throws Exception {

        doNothing().when(sesameJavaApplication).runService(any());
        SesamePlugin sesamePlugin = (SesamePlugin) lookupMojo("testing",
                new File(getBasedir(), "src/test/resources/basic-test-plugin-config.xml"));

        assertNotNull(sesamePlugin);

        sesamePlugin.setTimeout(1);
        sesamePlugin.setSesameJavaApplication(sesameJavaApplication);
        sesamePlugin.execute();

        assertEquals("src/test/resources/config.txt", System.getProperty("config.file.path"));
        assertEquals("TEXT", System.getProperty("config.type"));
        assertEquals("src/test/resources/beans.xml", System.getProperty("config.file.path.application.context"));
        verify(sesameJavaApplication, new Times(1)).runService(any());


    }

}
