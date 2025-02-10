package com.example.multiplegranarymanager.A_ShuChengPackage.Bean.Product;


import java.util.List;

public class Data {
    private List<ProductInfo> productInfo;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }
}
