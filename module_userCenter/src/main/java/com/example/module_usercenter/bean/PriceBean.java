package com.example.module_usercenter.bean;

public class PriceBean {

    private String title;
    private String vipLevel;
    private double price;


    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public PriceBean(String title, String vipLevel, double price) {
        this.title = title;
        this.vipLevel = vipLevel;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
