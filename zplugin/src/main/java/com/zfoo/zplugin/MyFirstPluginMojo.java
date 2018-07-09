package com.zfoo.zplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-06 17:33
 */

@Mojo(name = "myFirstPlugin", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.TEST)
public class MyFirstPluginMojo extends AbstractMojo {

    @Parameter(defaultValue = "Hello Maven!")
    private String message;

    @Override
    public void execute() {
        getLog().info("my first plugin!!!");
        getLog().info(message);
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
