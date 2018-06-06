package com.zfoo.orm.entity;

import com.zfoo.orm.model.anno.Cache;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.anno.UniqueIndex;
import com.zfoo.orm.model.entity.IEntity;

import javax.persistence.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-16 17:37
 */
@Cache(size = "hundred", persister = @Persister("3s"))
@Entity
@NamedQueries({
        @NamedQuery(name = "testQuery", query = "select e from PlayerEnt"),
        @NamedQuery(name = "indexQuery", query = "from PlayerEnt where a = ?"),
        @NamedQuery(name = "uniqueQuery", query = "from PlayerEnt where b = ?"),
        @NamedQuery(name = "pagedQuery", query = "from PlayerEnt where id >= ?")
})
public class PlayerEnt implements IEntity<Long> {

    @Id
    @Column
    private long id;

    @Index(namedQuery = "indexQuery")
    @Column
    private byte a;

    @UniqueIndex(namedQuery = "uniqueQuery")
    @Column
    private short b;

    @Column
    private int c;

    @Column
    private boolean d;

    @Lob
    private String e;

    public PlayerEnt() {
    }


    public PlayerEnt(long id, byte a, short b, int c, boolean d, String e) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

        this.e = e;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getA() {
        return a;
    }

    public void setA(byte a) {
        this.a = a;
    }

    public short getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "PlayerEnt{" +
                "id=" + id +
                ", a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e='" + e + '\'' +
                '}';
    }
}
