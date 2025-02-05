package com.project_250131.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    public List<Map<String, Object>> selectUserListTest();
}
