package com.project_250131.bookmark.mapper;

import com.project_250131.bookmark.domain.Bookmark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookmarkMapper {

    public int selectBookmarkCountByStoreId(int storeId);

    public Bookmark selectBookmarkByUserIdStoreId(
            @Param("userId") int userId,
            @Param("storeId") int storeId);
}
