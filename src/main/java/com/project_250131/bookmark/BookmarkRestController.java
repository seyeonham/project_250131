package com.project_250131.bookmark;

import com.project_250131.bookmark.bo.BookmarkBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookmarkRestController {

    private final BookmarkBO bookmarkBO;

    @PostMapping("/bookmark/store")
    public Map<String, Object> bookmarkStore(
            @RequestParam("storeId") int storeId,
            HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
        } else {
            int rowCount = bookmarkBO.updateBookmark(userId, storeId);
            if (rowCount > 0) {
                result.put("code", 200);
                result.put("result", "성공");
            } else {
                result.put("code", 500);
                result.put("error_message", "북마크하는데 실패했습니다.");
            }
        }

        return result;
    }
}
