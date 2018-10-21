package com.zfoo.river.module.user.manager;

import com.zfoo.orm.model.anno.OrmInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import com.zfoo.river.module.user.entity.UserEntity;
import com.zfoo.river.module.user.resource.UserPrivilegeResource;
import com.zfoo.storage.model.anno.ResInjection;
import com.zfoo.storage.model.vo.Storage;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/10/20
 */
@Component
public class UserManager implements IUserManager {

    @OrmInjection
    private IEntityCaches<Long, UserEntity> userCaches;

    @ResInjection
    private Storage<String, UserPrivilegeResource> userPrivilegeResources;


    @Override
    public void addUser(UserEntity entity) {
        userCaches.insert(entity);
    }
}
