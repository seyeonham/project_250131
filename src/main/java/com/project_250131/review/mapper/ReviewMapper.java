package com.project_250131.review.mapper;

import com.project_250131.review.domain.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    public List<Review> selectReviewListByStoreId(int storeId);
}
