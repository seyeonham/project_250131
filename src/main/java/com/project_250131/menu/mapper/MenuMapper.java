package com.project_250131.menu.mapper;

import com.project_250131.menu.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface MenuMapper {

    public Menu selectMenuByStoreId(int storeId);

    public List<Menu> selectMenuListByStoreId(int storeId);

    public Menu selectMenuById(int menuId);

    public int insertMenu(
            @Param("storeId") int storeId,
            @Param("imagePath") String imagePath,
            @Param("name") String name,
            @Param("price") int price
            );

    public int deleteMenu(int menuId);
}
