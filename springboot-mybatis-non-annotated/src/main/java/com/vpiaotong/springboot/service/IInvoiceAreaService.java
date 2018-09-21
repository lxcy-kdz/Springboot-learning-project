package com.vpiaotong.springboot.service;

import com.github.pagehelper.PageInfo;
import com.vpiaotong.springboot.entity.InvoiceArea;

public interface IInvoiceAreaService {

    PageInfo<InvoiceArea> selectAll();
}
