package com.project_250131.bookmark;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class BookmarkRestController {

    @PatchMapping("/bookmark/store")
    public Map<String, Object> bookmarkStore(
            @RequestParam("storeId") int storeId,
            HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
        }
    }
}
