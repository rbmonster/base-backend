package com.sw.shardingjdbc.dao;


import com.sw.shardingjdbc.model.NotificationEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationEventDao {
    int deleteByPrimaryKey(Integer id);

    int insert(NotificationEvent record);

    int insertSelective(NotificationEvent record);

    NotificationEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NotificationEvent record);

    int updateByPrimaryKey(NotificationEvent record);

    List<NotificationEvent> selectByIds();


    @Select("select * from notification_event limit 10")
    List<NotificationEvent> findUsers();
}