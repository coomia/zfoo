package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.14 12:36
 */
public class CM_Map implements IPacket {

    private static final transient short PROTOCOL_ID = 10;

    private HashMap<Integer, Integer> map;

    private Map<Integer, ObjectA> mapA;

    private Map<ObjectA, String> mapB;

    private Map<ObjectA, ObjectA> mapC;

    public static short getProtocolId() {
        return PROTOCOL_ID;
    }

    public HashMap<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Integer> map) {
        this.map = map;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    @Override
    public String toString() {
        return "CM_Map{" + "map=" + map + ", mapA=" + mapA + ", mapB=" + mapB + ", mapC=" + mapC + '}';
    }

    public Map<Integer, ObjectA> getMapA() {
        return mapA;
    }

    public void setMapA(Map<Integer, ObjectA> mapA) {
        this.mapA = mapA;
    }

    public Map<ObjectA, String> getMapB() {
        return mapB;
    }

    public void setMapB(Map<ObjectA, String> mapB) {
        this.mapB = mapB;
    }

    public Map<ObjectA, ObjectA> getMapC() {
        return mapC;
    }

    public void setMapC(Map<ObjectA, ObjectA> mapC) {
        this.mapC = mapC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CM_Map cm_map = (CM_Map) o;
        return Objects.equals(map, cm_map.map) &&
                Objects.equals(mapA, cm_map.mapA) &&
                Objects.equals(mapB, cm_map.mapB) &&
                Objects.equals(mapC, cm_map.mapC);
    }

    @Override
    public int hashCode() {

        return Objects.hash(map, mapA, mapB, mapC);
    }
}
