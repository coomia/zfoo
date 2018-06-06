package com.zfoo.net.server.manager;

import com.zfoo.net.server.model.Session;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.16 11:35
 */
public interface ISessionManager {

    void addSession(Session session);

    void removeSession(Session session);

    Session getSession(Long id);

    Map<Long, Session> getSessionMap();

}
