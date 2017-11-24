package com.upa.petshop_intern.module.custom;

import javax.validation.constraints.Min;

/**
 * Created by Yunhao.Cao on 2017/10/17.
 */
public class CustomRechargeForm {
    @Min(value = 0)
    private Double rechargeNumber;

    public Double getRechargeNumber() {
        return rechargeNumber;
    }

    public void setRechargeNumber(Double rechargeNumber) {
        this.rechargeNumber = rechargeNumber;
    }
}
