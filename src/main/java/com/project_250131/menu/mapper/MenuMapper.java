package com.project_250131.menu.mapper;

import com.project_250131.menu.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    public Menu selectMenuByStoreId(int storeId);

    public List<Menu> selectMenuListByStoreId(int storeId);
}
