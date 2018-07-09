package com.zfoo.zplugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-07-05 20:48
 */


// 在Compile生命周期调用，必须依赖Test完成才能执行
@Mojo(name = "mySecondPlugin", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.TEST)
public class MySecondPluginMojo extends AbstractMojo {

    @Override
    public void execute() {
        getLog().info("my second plugin!!!");
    }

}
