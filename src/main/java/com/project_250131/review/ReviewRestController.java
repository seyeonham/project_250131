package com.project_250131.review;

import com.project_250131.review.bo.ReviewBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewRestController {

    private final ReviewBO reviewBO;

    @PostMapping("/create")
    public Map<String, Object> create(
            @RequestParam("storeId") int storeId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("point") int point,
            @RequestParam(value = "content", required = false) String content,
            HttpSession session
            ) {

        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
        } else {
            int rowCount = reviewBO.addReview(storeId, userId, file, point, content);
            if (rowCount > 0) {
                result.put("code", 200);
                result.put("result", "성공");
            } else {
                result.put("code", 500);
                result.put("error_message", "리뷰 작성에 실패했습니다.");
            }
        }
        return result;
    }

    @DeleteMapping("/delete")
    public Map<String, Object> delete(
            @RequestParam("reviewId") int reviewId,
            HttpSession session
    ){
        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
        } else {
            int rowCount = reviewBO.deleteReviewById(reviewId);
            if (rowCount > 0) {
                result.put("code", 200);
                result.put("result", "성공");
            } else {
                result.put("code", 500);
                result.put("error_message", "리뷰 삭제에 실패했습니다.");
            }
        }

        return result;
    }
}
