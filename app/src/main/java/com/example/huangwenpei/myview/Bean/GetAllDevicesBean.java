package com.example.huangwenpei.myview.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class GetAllDevicesBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nID : 435
         * strUser : 13170882739
         * strNickName :
         * strTEID : 356988552588666898602O6191680004974
         * strTEIDNickName :
         * nRoleID : 1
         * strIconID : photo//HeadIcon//default.png
         * strOwnerName : 秘籍
         * nHeight :
         * nWeight :
         * strTESim : 9484685
         * bWhiteList :
         * nBatteryPercent : 0
         */

        private String nID;
        private String strUser;
        private String strNickName;
        private String strTEID;
        private String strTEIDNickName;
        private String nRoleID;
        private String strIconID;
        private String strOwnerName;
        private String nHeight;
        private String nWeight;
        private String strTESim;
        private String bWhiteList;
        private String nBatteryPercent;

        public String getNID() {
            return nID;
        }

        public void setNID(String nID) {
            this.nID = nID;
        }

        public String getStrUser() {
            return strUser;
        }

        public void setStrUser(String strUser) {
            this.strUser = strUser;
        }

        public String getStrNickName() {
            return strNickName;
        }

        public void setStrNickName(String strNickName) {
            this.strNickName = strNickName;
        }

        public String getStrTEID() {
            return strTEID;
        }

        public void setStrTEID(String strTEID) {
            this.strTEID = strTEID;
        }

        public String getStrTEIDNickName() {
            return strTEIDNickName;
        }

        public void setStrTEIDNickName(String strTEIDNickName) {
            this.strTEIDNickName = strTEIDNickName;
        }

        public String getNRoleID() {
            return nRoleID;
        }

        public void setNRoleID(String nRoleID) {
            this.nRoleID = nRoleID;
        }

        public String getStrIconID() {
            return strIconID;
        }

        public void setStrIconID(String strIconID) {
            this.strIconID = strIconID;
        }

        public String getStrOwnerName() {
            return strOwnerName;
        }

        public void setStrOwnerName(String strOwnerName) {
            this.strOwnerName = strOwnerName;
        }

        public String getNHeight() {
            return nHeight;
        }

        public void setNHeight(String nHeight) {
            this.nHeight = nHeight;
        }

        public String getNWeight() {
            return nWeight;
        }

        public void setNWeight(String nWeight) {
            this.nWeight = nWeight;
        }

        public String getStrTESim() {
            return strTESim;
        }

        public void setStrTESim(String strTESim) {
            this.strTESim = strTESim;
        }

        public String getBWhiteList() {
            return bWhiteList;
        }

        public void setBWhiteList(String bWhiteList) {
            this.bWhiteList = bWhiteList;
        }

        public String getNBatteryPercent() {
            return nBatteryPercent;
        }

        public void setNBatteryPercent(String nBatteryPercent) {
            this.nBatteryPercent = nBatteryPercent;
        }
    }
}
