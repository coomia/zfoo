package com.zfoo.game.gameserver.module.player.entity;

import com.zfoo.game.gameserver.module.player.model.Player;
import com.zfoo.orm.model.anno.Cache;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.anno.UniqueIndex;
import com.zfoo.orm.model.entity.IEntity;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-11-16 17:37
 */
@Cache(size = "hundred", persister = @Persister("3s"))
@Entity
@NamedQueries({
        @NamedQuery(name = "uniqueQuery", query = "from PlayerEntity where id = ?"),
        @NamedQuery(name = "indexQuery", query = "from PlayerEntity where age = ?"),
        @NamedQuery(name = "pagedQuery", query = "from PlayerEntity where id >= ?")
})
public class PlayerEntity implements IEntity<Long> {

    @Transient
    private AtomicReference<Player> playerRef = new AtomicReference<>();// 业务player对象

    @UniqueIndex(namedQuery = "uniqueQuery")
    @Id
    @Column
    private long id;

    @Index(namedQuery = "indexQuery")
    @Column
    private int age;

    @Column
    private short loginNum;

    @Column
    private boolean man;// 性别，false男，true女

    @Lob
    private String name;

    public PlayerEntity() {
    }

    public PlayerEntity(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        Player player = playerRef.get();
        if (player == null) {

        }
        return playerRef.get();
    }

    public void setPlayer(Player player) {
        playerRef.compareAndSet(null, player);
    }

    @Override
    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(short loginNum) {
        this.loginNum = loginNum;
    }
}
