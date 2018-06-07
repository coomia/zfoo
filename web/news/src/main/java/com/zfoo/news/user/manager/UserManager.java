package com.zfoo.news.user.manager;

import com.zfoo.news.user.resource.UserPrivilegeResource;
import com.zfoo.storage.model.anno.ResInjection;
import com.zfoo.storage.model.vo.Storage;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-06-06 20:28
 */
@Component
public class UserManager implements IUserManager {

    @ResInjection
    Storage<String, UserPrivilegeResource> userPrivilegeResources;


}
