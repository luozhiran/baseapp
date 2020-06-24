package com.yk.baseapp.abc;

import java.util.List;

public class Abc1 {

    private String areaId;
    private String name;
    private List<SonsData> sons;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SonsData> getSons() {
        return sons;
    }

    public void setSons(List<SonsData> sons) {
        this.sons = sons;
    }

    public static class SonsData {
        /**
         * areaId : 3116
         * name : 安庆
         * sons : null
         */

        private String areaId;
        private String name;
        private Object sons;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSons() {
            return sons;
        }

        public void setSons(Object sons) {
            this.sons = sons;
        }
    }
}
