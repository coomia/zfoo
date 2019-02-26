package com.zfoo.orm.entity;

import com.zfoo.orm.model.anno.Cache;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;

import javax.persistence.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-08 13:34
 */
@Cache(size = "hundred", persister = @Persister("0s"))
@Entity
@NamedQueries({@NamedQuery(name = "mailEntIndexQuery", query = "from MailEnt where playName = ?0")})
public class MailEnt implements IEntity<String> {

    @Id
    @Column
    private String mailId;

    @Index(namedQuery = "uniqueQuery")
    private String playerName;

    private String content;

    public MailEnt() {
    }

    public MailEnt(String mailId, String playerName, String content) {
        this.mailId = mailId;
        this.playerName = playerName;
        this.content = content;
    }

    @Override
    public String getId() {
        return mailId;
    }


    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailEnt{" +
                "mailId='" + mailId + '\'' +
                ", playName='" + playerName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
