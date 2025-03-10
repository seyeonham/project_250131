package com.project_250131.bookmark.mapper;

import com.project_250131.bookmark.domain.Bookmark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {

    int selectBookmarkCountByStoreId(int storeId);

    Bookmark selectBookmarkByUserIdStoreId(
            @Param("userId") int userId,
            @Param("storeId") int storeId);

    List<Bookmark> selectBookmarkByUserIdDeleteYn(int userId);

    int selectBookmarkCountByUserIdDeleteYn(int userId);

    int updateBookmarkByUserIdStoreId(
            @Param("userId") int userId,
            @Param("storeId") int storeId);

    int insertBookmark(
            @Param("userId") int userId,
            @Param("storeId") int storeId);
}
