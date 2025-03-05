package com.project_250131.bookmark.mapper;

import com.project_250131.bookmark.domain.Bookmark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {

    public int selectBookmarkCountByStoreId(int storeId);

    public Bookmark selectBookmarkByUserIdStoreId(
            @Param("userId") int userId,
            @Param("storeId") int storeId);

    public List<Bookmark> selectBookmarkByUserIdDeleteYn(int userId);

    public int selectBookmarkCountByUserIdDeleteYn(int userId);

    public int updateBookmarkByUserIdStoreId(
            @Param("userId") int userId,
            @Param("storeId") int storeId);

    public int insertBookmark(
            @Param("userId") int userId,
            @Param("storeId") int storeId);
}
