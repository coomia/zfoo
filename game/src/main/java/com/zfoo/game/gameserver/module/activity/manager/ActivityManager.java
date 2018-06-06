package com.zfoo.game.gameserver.module.activity.manager;

import com.zfoo.game.gameserver.module.activity.resource.ActivityResource;
import com.zfoo.storage.model.anno.ResInjection;
import com.zfoo.storage.model.vo.Storage;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 13:36
 */
@Component
public class ActivityManager implements IActivityManager {

    @ResInjection
    Storage<Integer, ActivityResource> resources;

    @Override
    public ActivityResource getActivityResource(int id) {
        return resources.get(id);
    }
}
