package com.zfoo.net;

import com.zfoo.net.dispatcher.manager.IPacketDispatcherManager;
import com.zfoo.net.protocol.manager.IProtocolManager;
import com.zfoo.net.protocol.service.IProtocolService;
import com.zfoo.net.server.Server;
import com.zfoo.net.server.manager.ISessionManager;
import com.zfoo.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.11 18:02
 */
public class NetContext extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

    private static NetContext instance;

    private static String protocolLocation;

    private ApplicationContext applicationContext;

    private IProtocolManager protocolManager;

    private IProtocolService protocolService;

    private IPacketDispatcherManager dispatcherManager;

    private ISessionManager sessionManager;

    private Server server;

    public NetContext(String protocolLocation) {
        NetContext.protocolLocation = protocolLocation;
    }

    public static ApplicationContext getApplicationContext() {
        return instance.applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (NetContext.instance == null) {
            NetContext.instance = this;
            instance.applicationContext = applicationContext;
            instance.protocolManager = applicationContext.getBean(IProtocolManager.class);
            instance.protocolService = applicationContext.getBean(IProtocolService.class);
            instance.sessionManager = applicationContext.getBean(ISessionManager.class);
            instance.dispatcherManager = applicationContext.getBean(IPacketDispatcherManager.class);
            instance.server = (Server) applicationContext.getBean(StringUtils.uncapitalize(Server.class.getSimpleName()));
            NetContext.getProtocolManager().parseProtocol(protocolLocation);
        }
    }

    public static IProtocolManager getProtocolManager() {
        return instance.protocolManager;
    }

    public static IProtocolService getProtocolService() {
        return instance.protocolService;
    }

    public static ISessionManager getSessionManager() {
        return instance.sessionManager;
    }

    public static IPacketDispatcherManager getDispatcherManager() {
        return instance.dispatcherManager;
    }

    public static Server getServer() {
        return instance.server;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
