package com.vpiaotong.springboot.test;

import com.github.pagehelper.PageInfo;
import com.vpiaotong.springboot.entity.InvoiceArea;
import com.vpiaotong.springboot.service.IInvoiceAreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceAreaServiceTest {

    @Autowired
    private IInvoiceAreaService iInvoiceAreaService;

    @Test
    public void selectAll(){
        PageInfo<InvoiceArea> invoiceAreas = iInvoiceAreaService.selectAll();
        System.out.println(invoiceAreas);
    }
}
