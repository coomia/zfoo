package com.zfoo.net.server.manager;

import com.zfoo.net.server.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 11:35
 */
public class SessionManager implements ISessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private Map<Long, Session> sessionMap = new ConcurrentHashMap<>();


    @Override
    public void addSession(Session session) {
        if (sessionMap.containsKey(session.getId())) {
            logger.error("重复的[session:{}]", session);
            return;
        }
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void removeSession(Session session) {
        if (!sessionMap.containsKey(session.getId())) {
            logger.error("SessionManager没有包含[session:{}]，所以无法移除", session);
            return;
        }
        sessionMap.remove(session.getId());
    }

    @Override
    public Session getSession(Long id) {
        return sessionMap.get(id);
    }

    @Override
    public Map<Long, Session> getSessionMap() {
        return sessionMap;
    }
}
