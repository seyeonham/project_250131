package com.project_250131.review.bo;

import com.project_250131.common.FileManagerService;
import com.project_250131.review.domain.Review;
import com.project_250131.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewBO {

    private final ReviewMapper reviewMapper;
    private final FileManagerService fileManagerService;

    public double getReviewPointByStoreId(int storeId) {
        Double point = reviewMapper.selectReviewPointByStoreId(storeId);
        if (point == null) {
            return 0;
        }
        return point;
    }

    public List<Review> getReviewListByStoreId(int storeId) {
        return reviewMapper.selectReviewListByStoreId(storeId);
    }

    public int addReview(int storeId, int userId, MultipartFile file, int point, String content) {
        String imagePath = null;
        if (file != null) {
            imagePath = fileManagerService.uploadReviewFile(file, storeId, userId);
        }

        int rowCount = reviewMapper.insertReview(storeId, userId, imagePath, point, content);
        return rowCount;
    }

    public int deleteReviewById(int id) {
        Review review = reviewMapper.selectReviewById(id);
        fileManagerService.deleteFile(review.getImagePath());
        int rowCount = reviewMapper.deleteReviewById(id);
        return rowCount;
    }
}
