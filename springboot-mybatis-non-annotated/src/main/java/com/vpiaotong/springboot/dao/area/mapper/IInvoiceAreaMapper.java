package com.vpiaotong.springboot.dao.area.mapper;

import com.vpiaotong.springboot.entity.InvoiceArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IInvoiceAreaMapper {

    /**
     * 查询所有发票地区
     * @return 查询结果集合
     */
    List<InvoiceArea> selectAll();
}
