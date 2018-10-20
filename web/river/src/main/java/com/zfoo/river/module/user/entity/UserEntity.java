package com.zfoo.river.module.user.entity;

import com.zfoo.orm.model.anno.Cache;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;

import javax.persistence.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/20
 */
@Cache(size = "hundred", persister = @Persister("3s"))
@Entity
@NamedQueries({@NamedQuery(name = "selectAllUser", query = "from UserEntity")})
public class UserEntity implements IEntity<Integer> {

    @Id
    @Column
    private int id;

    private String name;

    private String password;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
