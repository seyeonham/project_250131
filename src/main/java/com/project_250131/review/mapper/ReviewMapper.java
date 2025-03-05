package com.project_250131.review.mapper;

import com.project_250131.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {

    public Double selectReviewPointByStoreId(int storeId);
    public List<Review> selectReviewListByStoreId(int storeId);
    public Review selectReviewById(int id);
    public List<Review> selectReviewListByUserId(int userId);
    public int selectReviewCountByUserId(int userId);
    public Integer selectRegularReviewCountByUserId(int userId);

    public int insertReview(
            @Param("storeId") int storeId,
            @Param("userId") int userId,
            @Param("imagePath") String imagePath,
            @Param("point") int point,
            @Param("content") String content);

    public int deleteReviewById(int id);
}
