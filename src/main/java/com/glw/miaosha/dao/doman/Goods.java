package com.glw.miaosha.dao.doman;


import lombok.Data;

@Data
public class Goods {
    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private Long goodsPrice;

    private Integer goodsStock;

    private String goodsDetail;
}