package com.qdu.applet.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PairMap {
    private Map<String, Integer> nameMap = new LinkedHashMap<>();
    private Map<Integer, String> idMap = new LinkedHashMap<>();

    public Map<String, Integer> getNameMap() {
        return nameMap;
    }

    public void setNameMap(Map<String, Integer> nameMap) {
        this.nameMap = nameMap;
    }

    public Map<Integer, String> getIdMap() {
        return idMap;
    }

    public void setIdMap(Map<Integer, String> idMap) {
        this.idMap = idMap;
    }

    public PairMap put(String name, Integer id) {
        this.nameMap.put(name, id);
        this.idMap.put(id, name);
        return this;
    }

    public Integer get(String name) {
        return this.nameMap.get(name);
    }

    public String get(Integer id) {
        return this.idMap.get(id);
    }

    @Override
    public String toString() {
        String result = "";
        for (Integer key : idMap.keySet()) {
            result += idMap.get(key) + " : " + key + "\r\n";
        }
        return result;
    }

    public PairMap putAll(PairMap pairMap) {
        this.nameMap.putAll(pairMap.getNameMap());
        this.idMap.putAll(pairMap.getIdMap());
        return this;
    }

    public Iterator<Integer> getIdIntegrator() {
        return this.idMap.keySet().iterator();
    }

    public Iterator<String> getNameIntegrator() {
        return this.nameMap.keySet().iterator();
    }
}
