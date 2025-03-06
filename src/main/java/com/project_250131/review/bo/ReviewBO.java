package com.project_250131.review.bo;

import com.project_250131.common.FileManagerService;
import com.project_250131.review.domain.Review;
import com.project_250131.review.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public List<Review> getReviewListByUserId(int userId) {
        return reviewMapper.selectReviewListByUserId(userId);
    }

    public int getReviewCountByUserId(int userId) {
        return reviewMapper.selectReviewCountByUserId(userId);
    }

    public int getRegularReviewCountByUserId(int userId) {
        Integer count = reviewMapper.selectRegularReviewCountByUserId(userId);
        return count != null ? count : 0;
    }

    public int getReviewCountByStoreId(int storeId) {
        return reviewMapper.selectReviewCountByStoreId(storeId);
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
        if (review.getImagePath() != null) {
            fileManagerService.deleteFile(review.getImagePath());
        }
        int rowCount = reviewMapper.deleteReviewById(id);
        return rowCount;
    }
}
