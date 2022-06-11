package model;

import groovy.lang.*;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class GroovyConfigurable extends GroovyObjectSupport {

    public void postProcess() throws InstantiationException, IllegalAccessException, NoSuchFieldException,
            NoSuchMethodException, InvocationTargetException {
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            Object value = getProperty(metaProperty.getName());
            if (Collection.class.isAssignableFrom(metaProperty.getType()) &&
                    value instanceof Collection) {
                ParameterizedType collectionType = (ParameterizedType)
                        getClass().getDeclaredField(metaProperty.getName()).getGenericType();
                Class itemClass = (Class) collectionType.getActualTypeArguments()[0];
                if (GroovyConfigurable.class.isAssignableFrom(itemClass)) {
                    Collection collection = (Collection) value;
                    Collection newValue = collection.getClass().newInstance();
                    for (Object o : collection) {
                        if (o instanceof Closure) {
                            Object item = itemClass.getConstructor().newInstance();
                            ((Closure) o).setDelegate(item);
                            ((Closure) o).setResolveStrategy(Closure.DELEGATE_FIRST);
                            ((Closure) o).call();
                            ((GroovyConfigurable) item).postProcess();
                            newValue.add(item);
                        } else {
                            newValue.add(o);
                        }
                    }
                    setProperty(metaProperty.getName(), newValue);
                }
            }
        }
    }

    public void configure(String filePath, boolean needsPostProcessing)
            throws IOException, NoSuchFieldException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(new Binding(), cc);
        DelegatingScript script = (DelegatingScript) sh.parse(new File(filePath));
        script.setDelegate(this);
        script.run();
        if (needsPostProcessing) {
            this.postProcess();
        }
    }
}
