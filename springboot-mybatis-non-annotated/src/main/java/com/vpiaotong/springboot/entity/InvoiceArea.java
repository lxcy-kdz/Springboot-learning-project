package com.vpiaotong.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class InvoiceArea implements Serializable {

    private static final long serialVersionUID = 1977518905911479989L;

    private Integer id;

    private String area;

    private String code;
}
