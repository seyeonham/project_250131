package com.project_250131.store.bo;

import com.project_250131.bookmark.bo.BookmarkBO;
import com.project_250131.menu.bo.MenuBO;
import com.project_250131.menu.domain.Menu;
import com.project_250131.review.bo.ReviewBO;
import com.project_250131.review.domain.Review;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreBO {

    private final StoreRepository storeRepository;
    private final MenuBO menuBO;
    private final ReviewBO reviewBO;
    private final BookmarkBO bookmarkBO;

    public List<StoreEntity> getStoreList() {
        List<StoreEntity> storeList = storeRepository.findAll();
        return storeList;
    }

    public StoreEntity getStoreById(int id) {
        Optional<StoreEntity> store = storeRepository.findById(id);
        return store.orElse(null);
    }

    public List<StoreEntity> getStoreListByRegion(String region) {
        List<StoreEntity> storeList = storeRepository
                .findByRoadAddressContainingOrStreetAddressContaining(region, region);
        return storeList;
    }

    public int getStoreCountByRegion(String region) {
        int count = storeRepository
                .countByRoadAddressContainingOrStreetAddressContaining(region, region);
        return count;
    }

    public List<StoreEntity> getStoreListByContinent(String continent) {
        List<StoreEntity> storeList = storeRepository.findByContinentContaining(continent);
        return storeList;
    }

    public int getStoreCountByContinent(String continent) {
        int count = storeRepository.countByContinent(continent);
        return count;
    }

    public List<StoreListDTO> generateStoreList(int userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<StoreEntity> stores = getStoreList();

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();

            storeListDTO.setMenuImage(imagePath);

            // 리뷰 평점

            // 북마크 여부
            boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
            storeListDTO.setBookmark(bookmark);

            storeList.add(storeListDTO);
        }

        return storeList;
    }

    public List<StoreListDTO> generateStoreListByRegion(String region) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<StoreEntity> stores = getStoreListByRegion(region);

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();

            storeListDTO.setMenuImage(imagePath);

            // 리뷰 평점


            storeList.add(storeListDTO);
        }

        return storeList;
    }

    public List<StoreListDTO> generateStoreListByContinent(String continent) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<StoreEntity> stores = getStoreListByContinent(continent);

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();

            storeListDTO.setMenuImage(imagePath);

            // 리뷰 평점


            storeList.add(storeListDTO);
        }

        return storeList;
    }

    public StoreDetailDTO generateStoreByStoreId(int storeId) {
        StoreDetailDTO storeDetailDTO = new StoreDetailDTO();

        // 맛집 정보
        StoreEntity store = getStoreById(storeId);
        storeDetailDTO.setStore(store);

        // 메뉴
        List<Menu> menuList = menuBO.getMenuListByStoreId(storeId);
        storeDetailDTO.setMenuList(menuList);

        // 리뷰
        List<Review> reviewList = reviewBO.getReviewListByStoreId(storeId);
        storeDetailDTO.setReviewList(reviewList);

        // 북마크 개수
        int bookmarkCount = bookmarkBO.getBookmarkCountByStoreId(storeId);
        storeDetailDTO.setBookmarkCount(bookmarkCount);

        return storeDetailDTO;
    }
}
