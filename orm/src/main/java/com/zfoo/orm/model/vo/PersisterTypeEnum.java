package com.zfoo.orm.model.vo;

import com.zfoo.orm.model.persister.IPersister;
import com.zfoo.orm.model.persister.QueuePersister;
import com.zfoo.orm.model.persister.TimePersister;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 13:12
 */
public enum PersisterTypeEnum {

    QUEUE {
        @Override
        public IPersister createPersister(String persisterConfig) {
            QueuePersister persister = new QueuePersister(persisterConfig);
            return persister;
        }
    },
    TIME {
        @Override
        public IPersister createPersister(String persisterConfig) {
            TimePersister persister = new TimePersister(persisterConfig);
            return persister;
        }
    };


    public abstract IPersister createPersister(String persisterConfig);

    public static PersisterTypeEnum getPersisterType(String persisterType) {
        for (PersisterTypeEnum persister : values()) {
            if (persister.name().equalsIgnoreCase(persisterType)) {
                return persister;
            }
        }
        FormattingTuple message = MessageFormatter.format("无效的持久化类型[persisterType:{}]", persisterType);
        throw new IllegalArgumentException(message.getMessage());
    }

}
