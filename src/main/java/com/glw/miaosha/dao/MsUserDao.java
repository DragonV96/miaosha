package com.glw.miaosha.dao;

import com.glw.miaosha.doman.MsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author glw
 * @date 2019/5/8 10:40
 */
@Mapper
public interface MsUserDao {

    @Select("select * from ms_user where id=#{id}")
    public MsUser getById(@Param("id") long id);
}