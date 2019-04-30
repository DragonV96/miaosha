package com.glw.miaosha.dao;

import com.glw.miaosha.doman.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author glw
 * @date 2019/4/30 14:07
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user(id, name) values(#{id},#{name})")
    public int insert(User user);
}
