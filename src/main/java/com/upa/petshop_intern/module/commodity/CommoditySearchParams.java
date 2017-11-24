package com.upa.petshop_intern.module.commodity;

import com.upa.petshop_intern.common.page.PageParams;

/**
 * Created by Yunhao.Cao on 2017/10/13.
 */
public class CommoditySearchParams extends PageParams {
    private String q;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
