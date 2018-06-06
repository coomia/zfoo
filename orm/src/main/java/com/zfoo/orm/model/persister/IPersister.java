package com.zfoo.orm.model.persister;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 17:17
 */
public interface IPersister extends Runnable {

    void put(PNode node);

    void persist();

}
