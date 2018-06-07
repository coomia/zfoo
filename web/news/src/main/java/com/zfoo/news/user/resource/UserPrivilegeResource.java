package com.zfoo.news.user.resource;

import com.zfoo.storage.model.anno.Id;
import com.zfoo.storage.model.anno.Resource;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-07 11:49
 */
@Resource
public class UserPrivilegeResource {

    @Id
    private int userLevel;

    private String privilege;


    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
