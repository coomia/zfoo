package com.zfoo.orm.model.persister;

import com.zfoo.orm.OrmContext;
import com.zfoo.util.TimeUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 17:28
 */
public class TimePersister extends AbstractPersister {

    // 如果设置的时间不合法，默认用这个最小时间
    protected static final long MIN_INTERVAL_PERSISTER_TIME = 1000;

    private String persisterConfig;


    public TimePersister(String persisterConfig) {
        this.persisterConfig = persisterConfig;
    }

    @Override
    public void persist() {
        OrmContext.getOrmService().schedule(this, MIN_INTERVAL_PERSISTER_TIME);
    }

    @Override
    public Map<String, String> getInfos() {
        return null;
    }

    @Override
    public void run() {
        super.persist();
        Date currentDate = new Date();
        Date nextDate = TimeUtils.getNextDateByCronExpression(persisterConfig, currentDate);
        long delay = nextDate.getTime() - currentDate.getTime();
        if (MIN_INTERVAL_PERSISTER_TIME > delay) {
            delay = MIN_INTERVAL_PERSISTER_TIME;
        }
        OrmContext.getOrmService().schedule(this, delay);
    }
}
