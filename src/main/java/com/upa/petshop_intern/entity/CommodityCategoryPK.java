package com.upa.petshop_intern.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Li.Hou on 2017/10/11.
 */
public class CommodityCategoryPK implements Serializable {
    private long commodityId;
    private long categoryId;

    @Column(name = "commodity_id", nullable = false)
    @Id
    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    @Column(name = "category_id", nullable = false)
    @Id
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommodityCategoryPK that = (CommodityCategoryPK) o;

        if (commodityId != that.commodityId) return false;
        if (categoryId != that.categoryId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (commodityId ^ (commodityId >>> 32));
        result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
        return result;
    }
}
