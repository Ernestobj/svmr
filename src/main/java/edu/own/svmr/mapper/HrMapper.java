package edu.own.svmr.mapper;

import edu.own.svmr.model.Hr;

public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    /**
     * 通过用户名查找用户对象
     * @param username
     * @return
     */
    Hr loadUserByUsername(String username);
}