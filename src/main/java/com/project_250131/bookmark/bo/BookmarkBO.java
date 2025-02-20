package com.project_250131.bookmark.bo;

import com.project_250131.bookmark.domain.Bookmark;
import com.project_250131.bookmark.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            return true;
        } else {
            return false;
        }
    }
}
