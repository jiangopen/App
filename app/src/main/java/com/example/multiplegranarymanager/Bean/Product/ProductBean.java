package com.example.multiplegranarymanager.Bean.Product;

import java.util.List;

public class ProductBean {
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
        private List<ProductInfo> productInfo;

        public List<ProductInfo> getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(List<ProductInfo> productInfo) {
            this.productInfo = productInfo;
        }
    }
}
