package com.glw.miaosha.vo;

import com.glw.miaosha.dao.doman.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author glw
 * @date 2019/5/15 14:41
 */
@Data
public class GoodsVo extends Goods {

    private Double msPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
