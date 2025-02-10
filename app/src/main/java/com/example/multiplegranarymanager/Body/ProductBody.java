package com.example.multiplegranarymanager.Body;

public class ProductBody {
    private String productName;
    private String productKey;

    public ProductBody(String productName, String productKey) {
        this.productName = productName;
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }
}
