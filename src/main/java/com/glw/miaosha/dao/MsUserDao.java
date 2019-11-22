package com.glw.miaosha.dao;

import com.glw.miaosha.dao.doman.MsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author glw
 * @date 2019/5/8 10:40
 */
@Mapper
public interface MsUserDao {

    @Select("select * from ms_user where id=#{id}")
    public MsUser getById(@Param("id") long id);

    @Update("update ms_user set password = #{password} where id = #{id}")
    public void update(MsUser toBeUpdate);
}
