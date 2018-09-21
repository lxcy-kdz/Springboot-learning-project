package com.vpiaotong.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vpiaotong.springboot.dao.area.mapper.IInvoiceAreaMapper;
import com.vpiaotong.springboot.entity.InvoiceArea;
import com.vpiaotong.springboot.service.IInvoiceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IInvoiceAreaServiceImpl implements IInvoiceAreaService {

    @Autowired
    private IInvoiceAreaMapper invoiceAreaMapper;
    @Override
    public PageInfo<InvoiceArea> selectAll() {
        return  PageHelper.startPage(1, 10).
                doSelectPageInfo(()->invoiceAreaMapper.selectAll());
    }
}
