package com.zfoo.storage.model.scheam;

/**
 * 对应xml文件的resource标签
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 08.28 17:15
 */
public class ResourceSchemaDef {

    private String location;

    private String suffix;

    public ResourceSchemaDef(String location, String suffix) {
        this.location = location;
        this.suffix = suffix;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
