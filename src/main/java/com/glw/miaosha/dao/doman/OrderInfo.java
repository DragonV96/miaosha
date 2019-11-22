package com.glw.miaosha.dao.doman;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfo {
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long deliveryAddrId;

    private String goodsName;

    private Integer goodsCount;

    private Long goodsPrice;

    private Byte orderChannel;

    private Byte status;

    private Date createTime;

    private Date payDate;
}