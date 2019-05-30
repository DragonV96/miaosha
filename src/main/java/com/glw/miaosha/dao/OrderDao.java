package com.glw.miaosha.dao;

import com.glw.miaosha.doman.MsOrder;
import com.glw.miaosha.doman.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * Create by glw
 * 2019/5/16 23:13
 */
@Mapper
public interface OrderDao {

    @Select("select * from ms_order where user_id = #{userId} and goods_id = #{goodsId}")
    public MsOrder getMsOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId")long goodsId);

    @Insert("insert into order_info(user_id, goods_id, delivery_addr_id, goods_name, goods_count, goods_price, order_channel, status, create_time) " +
            "values(#{userId}, #{goodsId}, #{deliveryAddrId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createTime})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into ms_order (user_id, goods_id, order_id) values(#{userId}, #{goodsId}, #{orderId})")
    public int insertMsOrder(MsOrder msOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
}
