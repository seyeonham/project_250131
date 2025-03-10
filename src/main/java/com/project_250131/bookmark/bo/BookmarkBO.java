package com.project_250131.bookmark.bo;

import com.project_250131.bookmark.domain.Bookmark;
import com.project_250131.bookmark.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkBO {

    private final BookmarkMapper bookmarkMapper;

    public int getBookmarkCountByStoreId(int storeId) {
        return bookmarkMapper.selectBookmarkCountByStoreId(storeId);
    }

    public boolean getBookmarkByUserIdStoreId(int userId, int storeId) {
        Bookmark bookmark = bookmarkMapper.selectBookmarkByUserIdStoreId(userId, storeId);

        if (bookmark != null) {
            if (bookmark.getDeleteYn() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Bookmark> getBookmarkListByUserIdDeleteYn(int userId) {
        return bookmarkMapper.selectBookmarkByUserIdDeleteYn(userId);
    }

    public int getBookmarkCountByUserIdDeleteYn(int userId) {
        return bookmarkMapper.selectBookmarkCountByUserIdDeleteYn(userId);
    }

    public int updateBookmark(int userId, int storeId) {
        Bookmark bookmark = bookmarkMapper.selectBookmarkByUserIdStoreId(userId, storeId);
        int rowCount = 0;
        if (bookmark != null) {
            rowCount = bookmarkMapper.updateBookmarkByUserIdStoreId(userId, storeId);
        } else {
            rowCount = bookmarkMapper.insertBookmark(userId, storeId);
        }
        return rowCount;
    }
}
