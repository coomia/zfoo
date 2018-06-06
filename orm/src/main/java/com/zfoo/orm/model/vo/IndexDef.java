package com.zfoo.orm.model.vo;

import com.zfoo.orm.model.anno.Index;

import java.lang.reflect.Field;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-04 17:00
 */
public class IndexDef {

    private Field field;

    private String namedQuery;


    public IndexDef(Field field, Index index) {
        this.field = field;
        this.namedQuery = index.namedQuery();
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getNamedQuery() {
        return namedQuery;
    }

    public void setNamedQuery(String namedQuery) {
        this.namedQuery = namedQuery;
    }
}
