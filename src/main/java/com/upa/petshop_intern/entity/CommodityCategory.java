package com.upa.petshop_intern.entity;

import javax.persistence.*;

/**
 * Created by Li.Hou on 2017/10/11.
 */
@Entity
@Table(name = "commodity_category", schema = "petshop", catalog = "")
@IdClass(CommodityCategoryPK.class)
public class CommodityCategory {
    private long commodityId;
    private long categoryId;

    @Id
    @Column(name = "commodity_id", nullable = false)
    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    @Id
    @Column(name = "category_id", nullable = false)
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

        CommodityCategory that = (CommodityCategory) o;

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
