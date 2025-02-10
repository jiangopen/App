package com.example.multiplegranarymanager.Bean.ProductDetail;

import com.example.multiplegranarymanager.Bean.ValueBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductDetailBean {
    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private Date createdAt;
        private String description;
        private List<DeviceInfo> deviceInfo;
        private Map<String, ValueBean> extraInfo;
        private String productKey;
        private String productName;
        private int productType;
        private String protocolType;
        private String typeIdentify;
        private Date updatedAt;

        public Date getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<DeviceInfo> getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(List<DeviceInfo> deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public Map<String, ValueBean> getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(Map<String, ValueBean> extraInfo) {
            this.extraInfo = extraInfo;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(String protocolType) {
            this.protocolType = protocolType;
        }

        public String getTypeIdentify() {
            return typeIdentify;
        }

        public void setTypeIdentify(String typeIdentify) {
            this.typeIdentify = typeIdentify;
        }

        public Date getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
