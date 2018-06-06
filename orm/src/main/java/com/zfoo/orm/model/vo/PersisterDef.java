package com.zfoo.orm.model.vo;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 13:20
 */
public class PersisterDef {

    private PersisterTypeEnum persister;

    private String persisterConfig;

    private PersisterDef() {
    }

    public static PersisterDef valueOf(String persisterType, String persisterConfig) {
        PersisterDef persisterDef = new PersisterDef();
        persisterDef.setPersister(PersisterTypeEnum.getPersisterType(persisterType));
        persisterDef.setPersisterConfig(persisterConfig);
        return persisterDef;
    }


    public PersisterTypeEnum getPersister() {
        return persister;
    }

    public void setPersister(PersisterTypeEnum persister) {
        this.persister = persister;
    }

    public String getPersisterConfig() {
        return persisterConfig;
    }

    public void setPersisterConfig(String persisterConfig) {
        this.persisterConfig = persisterConfig;
    }
}
