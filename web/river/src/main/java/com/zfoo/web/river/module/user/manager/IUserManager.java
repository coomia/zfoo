package com.zfoo.web.river.module.user.manager;

import com.zfoo.web.river.module.user.entity.UserEntity;
import com.zfoo.web.river.module.user.resource.UserPrivilegeResource;

import java.util.Collection;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/20
 */
public interface IUserManager {

    void addUser(UserEntity entity);

    Collection<UserPrivilegeResource> getAllUserPrivilegeResource();
}
