package com.project_250131.review.mapper;

import com.project_250131.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface ReviewMapper {

    public Double selectReviewPointByStoreId(int storeId);
    public List<Review> selectReviewListByStoreId(int storeId);

    public int insertReview(
            @Param("storeId") int storeId,
            @Param("userId") int userId,
            @Param("imagePath") String imagePath,
            @Param("point") int point,
            @Param("content") String content);
}
