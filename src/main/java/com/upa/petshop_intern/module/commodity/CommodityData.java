package com.upa.petshop_intern.module.commodity;

import com.upa.petshop_intern.entity.Category;

import java.util.List;

/**
 * Created by Yunhao.Cao on 2017/10/16.
 */
public class CommodityData {
    private long id;
    private String commodityName;
    private double commodityPrice;
    private String commodityDescription;
    private long commodityLast;
    private long commoditySold;
    private List<Category> commodityCategoryList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityDescription() {
        return commodityDescription;
    }

    public void setCommodityDescription(String commodityDescription) {
        this.commodityDescription = commodityDescription;
    }

    public long getCommodityLast() {
        return commodityLast;
    }

    public void setCommodityLast(long commodityLast) {
        this.commodityLast = commodityLast;
    }

    public long getCommoditySold() {
        return commoditySold;
    }

    public void setCommoditySold(long commoditySold) {
        this.commoditySold = commoditySold;
    }

    public List<Category> getCommodityCategoryList() {
        return commodityCategoryList;
    }

    public void setCommodityCategoryList(List<Category> commodityCategoryList) {
        this.commodityCategoryList = commodityCategoryList;
    }
}
