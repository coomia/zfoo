package com.zfoo.orm.model.persister;

import com.zfoo.orm.OrmContext;
import com.zfoo.util.StringUtils;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 17:28
 */
public class QueuePersister extends AbstractPersister {

    // 如果设置的时间不合法，默认用这个最小时间
    protected static final long MIN_INTERVAL_PERSISTER_TIME = 1000;

    private String persisterConfig;

    // -1代表无界，正数代表有界队列
    private int maxSize;

    private long intervalPersisterTime;

    private boolean isActive;

    public QueuePersister(String persisterConfig) {
        this.persisterConfig = persisterConfig;
        String[] splits = persisterConfig.split(StringUtils.COLON);
        this.maxSize = Integer.valueOf(splits[0]);
        this.intervalPersisterTime = Long.valueOf(splits[1]);
        if (MIN_INTERVAL_PERSISTER_TIME >= this.intervalPersisterTime) {
            this.intervalPersisterTime = MIN_INTERVAL_PERSISTER_TIME;
        }
        this.isActive = false;
    }

    @Override
    public void put(PNode node) {
        if (maxSize <= 0 || persisterMap.size() <= maxSize) {
            super.put(node);
            return;
        }
    }

    @Override
    public void persist() {
        if (!isActive) {
            isActive = true;
            OrmContext.getOrmService().scheduleAtFixedRate(this, intervalPersisterTime);
        }
    }

    @Override
    public Map<String, String> getInfos() {
        return null;
    }

    @Override
    public void run() {
        super.persist();
    }
}
