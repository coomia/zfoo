package com.zfoo.storage.model.scheam;

/**
 * 对应xml文件的scan标签
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 08.28 17:15
 */
public class ScanSchemaDef {

    private String packageName;

    public ScanSchemaDef(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
