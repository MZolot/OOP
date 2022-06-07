import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import model.GroovyConfigurable;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ConfigReader {
    void configure(String filePath, GroovyConfigurable config, boolean needsPostProcessing)
            throws IOException, NoSuchFieldException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(new Binding(), cc);
        DelegatingScript script = (DelegatingScript)sh.parse(new File(filePath));
        script.setDelegate(config);
        script.run();
        if (needsPostProcessing) {
            config.postProcess();
        }
    }
}
