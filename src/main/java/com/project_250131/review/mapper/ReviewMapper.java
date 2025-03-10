package com.project_250131.review.mapper;

import com.project_250131.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    Double selectReviewPointByStoreId(int storeId);
    List<Review> selectReviewListByStoreId(int storeId);
    Review selectReviewById(int id);
    List<Review> selectReviewListByUserId(int userId);
    int selectReviewCountByUserId(int userId);
    Integer selectRegularReviewCountByUserId(int userId);
    int selectReviewCountByStoreId(int storeId);

    int insertReview(
            @Param("storeId") int storeId,
            @Param("userId") int userId,
            @Param("imagePath") String imagePath,
            @Param("point") int point,
            @Param("content") String content);

    int deleteReviewById(int id);
}
