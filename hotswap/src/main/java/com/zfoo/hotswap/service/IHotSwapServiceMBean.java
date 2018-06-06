package com.zfoo.hotswap.service;

import javax.management.MXBean;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-12 12:21
 */
@MXBean
public interface IHotSwapServiceMBean {

    /**
     * 热更新相对于项目路径的文件
     *
     * @param relativePath 相对路径
     */
    void hotSwapByRelativePath(String relativePath);

    /**
     * 热更新绝对路径的文件
     *
     * @param absolutePath
     */
    void hotSwapByAbsolutePath(String absolutePath);

    void logAllUpdateClassFileInfo();

}
