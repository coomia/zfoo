package com.zfoo.storage.manager;

import com.zfoo.storage.model.vo.Storage;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-29 20:23
 */
public interface IStorageManager {

    void init();

    Storage<?, ?> getStorage(Class<?> clazz);

}
