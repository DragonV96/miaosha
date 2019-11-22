package com.glw.miaosha.dao.doman;

import lombok.Data;

import java.util.Date;

@Data
public class MsGoods {
    private Long id;

    private Long goodsId;

    private Long msPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}