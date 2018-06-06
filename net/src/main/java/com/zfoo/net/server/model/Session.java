package com.zfoo.net.server.model;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.11 21:23
 */
public class Session {

    private static int index = 0;

    private volatile int threadId;// 玩家当前的线程Id

    private long id;// 这个应该等于playerId，但是现在没有游戏服务器，先这样
    private long createdTime = System.currentTimeMillis();
    private Channel channel;

    private String inetIp;//ip+port

    private String ip;
    private String port;

    // Session附带的属性参数
    private Map<String, Object> attributes = new ConcurrentHashMap<>();

    public Session(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel不能为空");
        }
        this.id = index++;
        this.channel = channel;
        this.ip = channel.remoteAddress().toString();
        String[] adds = ip.split(":");
        this.inetIp = adds[ 0 ].substring(1);
        this.port = adds[ 1 ];
    }

    @Override
    public String toString() {
        return "Session{" + "threadId=" + threadId + ", id=" + id + ", createdTime=" + createdTime + ", channel=" + channel + ", inetIp='" + inetIp + '\'' + ", ip='" + ip + '\'' + ", port='" + port + '\'' + ", attributes=" + attributes + '}';
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInetIp() {
        return inetIp;
    }

    public void setInetIp(String inetIp) {
        this.inetIp = inetIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
