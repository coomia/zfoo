package com.zfoo.hotswap.manager;

import com.zfoo.hotswap.model.ClassFileDef;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-12 11:44
 */
public interface IHotSwapManager {

    Map<String, ClassFileDef> getClassFileDefMap();

}
