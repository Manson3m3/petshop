package com.upa.petshop_intern.common.response;

import com.upa.petshop_intern.common.util.ConvertUtil;
import com.upa.petshop_intern.common.util.RowConverter;
import org.springframework.beans.BeanUtils;

import java.util.List;


/**
 * 生成列表response，返回查询需要的多个结果
 *
 * @author liang.zhou
 */
public class ListResponse<T> extends BaseResponse<List<T>> {
    public <From> ListResponse(List<From> dataList, RowConverter<From, T> rowConverter) throws Exception {
        data = ConvertUtil.convertList(dataList, rowConverter);
    }

    public ListResponse() throws Exception {

    }

    public static <From, T> ListResponse<T> copyListProperties(List<From> dataList, final Class<T> clazz) throws Exception {
        ListResponse listResponse = new ListResponse();
        listResponse.data = ConvertUtil.convertList(dataList, new RowConverter<From, T>() {
            @Override
            public T convertRow(From from) throws Exception {
                T t = (T) (clazz.newInstance());
                BeanUtils.copyProperties(from, t);
                return t;
            }
        });
        return listResponse;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
