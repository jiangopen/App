package com.example.multiplegranarymanager.Body.QiTiao;

import java.util.ArrayList;
import java.util.List;

public class N2AllStatusBody {
    private String QiTiaoMuoShi;
    private String DanQiJi;
    private ArrayList<Status> AllStatus;

    public N2AllStatusBody(String qiTiaoMuoShi, String danQiJi, ArrayList<Status> allStatus) {
        QiTiaoMuoShi = qiTiaoMuoShi;
        DanQiJi = danQiJi;
        AllStatus = allStatus;
    }

    public String getQiTiaoMuoShi() {
        return QiTiaoMuoShi;
    }

    public void setQiTiaoMuoShi(String qiTiaoMuoShi) {
        QiTiaoMuoShi = qiTiaoMuoShi;
    }

    public String getDanQiJi() {
        return DanQiJi;
    }

    public void setDanQiJi(String danQiJi) {
        DanQiJi = danQiJi;
    }

    public ArrayList<Status> getAllStatus() {
        return AllStatus;
    }

    public void setAllStatus(ArrayList<Status> allStatus) {
        AllStatus = allStatus;
    }

    public static class Status {
        private int Tag;
        private String moduile;
        private String Name;
        private String moudleName;
        private String granaryId;
        private String nickName;
        private String productKey;
        private String deviceKey;
        private String status;

        public Status(int tag, String Moduile, String name, String moudleName, String granaryId, String nickName, String productKey, String deviceKey, String status) {
            this.Tag = tag;
            this.moduile = Moduile;
            Name = name;
            this.moudleName = moudleName;
            this.granaryId = granaryId;
            this.nickName = nickName;
            this.productKey = productKey;
            this.deviceKey = deviceKey;
            this.status = status;
        }

        public int getTag() {
            return Tag;
        }

        public void setTag(int tag) {
            Tag = tag;
        }

        public String getModuile() {
            return moduile;
        }

        public void setModuile(String moduile) {
            this.moduile = moduile;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getMoudleName() {
            return moudleName;
        }

        public void setMoudleName(String moudleName) {
            this.moudleName = moudleName;
        }

        public String getGranaryId() {
            return granaryId;
        }

        public void setGranaryId(String granaryId) {
            this.granaryId = granaryId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getDeviceKey() {
            return deviceKey;
        }

        public void setDeviceKey(String deviceKey) {
            this.deviceKey = deviceKey;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
