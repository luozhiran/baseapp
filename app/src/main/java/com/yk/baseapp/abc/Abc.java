package com.yk.baseapp.abc;

import java.util.List;

public class Abc {
    private String provice;
    private List<CityData> city;

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public List<CityData> getCity() {
        return city;
    }

    public void setCity(List<CityData> city) {
        this.city = city;
    }

    public static class CityData {
        /**
         * adcode : 340100
         * name : 合肥
         */

        private String adcode;
        private String name;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
