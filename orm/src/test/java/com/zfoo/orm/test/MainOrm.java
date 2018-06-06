package com.zfoo.orm.test;

import com.zfoo.orm.entity.PlayerEnt;
import com.zfoo.orm.model.anno.OrmInjection;
import com.zfoo.orm.model.cache.IEntityCaches;
import org.springframework.stereotype.Component;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-09 10:47
 */
@Component
public class MainOrm {

    @OrmInjection
    private IEntityCaches<Long, PlayerEnt> entityCaches;

    public IEntityCaches<Long, PlayerEnt> getEntityCaches() {
        return entityCaches;
    }

}
