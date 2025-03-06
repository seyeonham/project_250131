package com.project_250131.store.bo;

import com.project_250131.bookmark.bo.BookmarkBO;
import com.project_250131.bookmark.domain.Bookmark;
import com.project_250131.menu.bo.MenuBO;
import com.project_250131.menu.domain.Menu;
import com.project_250131.review.bo.ReviewBO;
import com.project_250131.review.domain.Review;
import com.project_250131.review.domain.ReviewDTO;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import com.project_250131.user.bo.UserBO;
import com.project_250131.user.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StoreBO {

    private final StoreRepository storeRepository;
    private final MenuBO menuBO;
    private final ReviewBO reviewBO;
    private final BookmarkBO bookmarkBO;
    private final UserBO userBO;

    public Page<StoreEntity> getStoreList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        int size = pageable.getPageSize();
        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return storeRepository.findAll(pageable);
    }

    public List<StoreEntity> getStoreList() {
        return storeRepository.findAll();
    }

    public int getStoreCount() {
        return (int) storeRepository.count();
    }

    public StoreEntity getStoreById(int id) {
        Optional<StoreEntity> store = storeRepository.findById(id);
        return store.orElse(null);
    }

    public Page<StoreEntity> getStoreListByRegion(String region, Pageable pageable, String sort) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        int size = pageable.getPageSize();

        Pageable sortedPageable = null;
        if (sort.equals("default")) {
            sortedPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        } else if (sort.equals("rating")) {
            sortedPageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, ));
        } else if (sort.equals("review")) {

        }

        return storeRepository
                .findByRoadAddressContainingOrStreetAddressContaining(region, region, sortedPageable);
    }

    public int getStoreCountByRegion(String region) {
        int count = storeRepository
                .countByRoadAddressContainingOrStreetAddressContaining(region, region);
        return count;
    }

    public Page<StoreEntity> getStoreListByContinent(String continent, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        int size = pageable.getPageSize();
        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return storeRepository.findByContinentContaining(continent, pageable);
    }

    public int getStoreCountByContinent(String continent) {
        int count = storeRepository.countByContinentContaining(continent);
        return count;
    }

    public Page<StoreEntity> getStoreListByStoreName(String name, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        int size = pageable.getPageSize();
        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return storeRepository.findByStoreNameContaining(name, pageable);
    }

    public int getStoreCountByStoreName(String name) {
        int count = storeRepository.countByStoreNameContaining(name);
        return count;
    }

    // 전체 맛집 목록
    public List<StoreListDTO> generateStoreList(Integer userId, Pageable pageable) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreEntity> stores = getStoreList(pageable);

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            if (menuBO.getMenuByStoreId(storeId) != null) {
                String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                storeListDTO.setMenuImage(imagePath);
            }

            // 리뷰 평점
            double point = reviewBO.getReviewPointByStoreId(storeId);
            DecimalFormat df = new DecimalFormat("#.#");
            String dfAverage = df.format(point);
            storeListDTO.setReviewAverage(dfAverage);

            // 북마크 여부
            if (userId != null) {
                boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
                storeListDTO.setBookmark(bookmark);
            }

            storeList.add(storeListDTO);
        }

        return storeList;
    }

    // 지역별 맛집 목록
    public List<StoreListDTO> generateStoreListByRegion(Pageable pageable, String region, Integer userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreEntity> stores = getStoreListByRegion(region, pageable);

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            if (menuBO.getMenuByStoreId(storeId) != null) {
                String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                storeListDTO.setMenuImage(imagePath);
            }

            // 리뷰 평점
            double point = reviewBO.getReviewPointByStoreId(storeId);
            DecimalFormat df = new DecimalFormat("#.#");
            String dfAverage = df.format(point);
            storeListDTO.setReviewAverage(dfAverage);

            // 북마크 여부
            if (userId != null) {
                boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
                storeListDTO.setBookmark(bookmark);
            }

            storeList.add(storeListDTO);
        }
        return storeList;
    }

    // 대륙별 맛집 목록
    public List<StoreListDTO> generateStoreListByContinent(Pageable pageable, String continent, Integer userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreEntity> stores = getStoreListByContinent(continent, pageable);

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            if (menuBO.getMenuByStoreId(storeId) != null) {
                String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                storeListDTO.setMenuImage(imagePath);
            }

            // 리뷰 평점
            double point = reviewBO.getReviewPointByStoreId(storeId);
            DecimalFormat df = new DecimalFormat("#.#");
            String dfAverage = df.format(point);
            storeListDTO.setReviewAverage(dfAverage);

            // 북마크 여부
            if (userId != null) {
                boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
                storeListDTO.setBookmark(bookmark);
            }

            storeList.add(storeListDTO);
        }

        return storeList;
    }

    // 이름별 맛집 목록
    public List<StoreListDTO> generateStoreListByStoreName(Pageable pageable, String name, Integer userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreEntity> stores = getStoreListByStoreName(name, pageable);

        for (StoreEntity storeEntity : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);
            int storeId = storeEntity.getId();

            // 메뉴 대표 이미지
            if (menuBO.getMenuByStoreId(storeId) != null) {
                String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                storeListDTO.setMenuImage(imagePath);
            }

            // 리뷰 평점
            double point = reviewBO.getReviewPointByStoreId(storeId);
            DecimalFormat df = new DecimalFormat("#.#");
            String dfAverage = df.format(point);
            storeListDTO.setReviewAverage(dfAverage);

            // 북마크 여부
            if (userId != null) {
                boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
                storeListDTO.setBookmark(bookmark);
            }

            storeList.add(storeListDTO);
        }

        return storeList;
    }

    // 북마크한 맛집 목록
    public List<StoreListDTO> generateBookmarkStoreList(Pageable pageable, int userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<Bookmark> bookmarkList = bookmarkBO.getBookmarkListByUserIdDeleteYn(userId);

        List<StoreEntity> storeEntityList = getStoreList();

        for (StoreEntity storeEntity : storeEntityList) {
            StoreListDTO storeListDTO = new StoreListDTO();

            for (Bookmark bookmark : bookmarkList) {
                if (bookmark.getStoreId() == storeEntity.getId()) {

                    // 맛집 1개
                    storeListDTO.setStore(storeEntity);
                    int storeId = storeEntity.getId();

                    // 메뉴 대표 이미지
                    if (menuBO.getMenuByStoreId(storeId) != null) {
                        String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                        storeListDTO.setMenuImage(imagePath);
                    }

                    // 리뷰 평점
                    double point = reviewBO.getReviewPointByStoreId(storeId);
                    DecimalFormat df = new DecimalFormat("#.#");
                    String dfAverage = df.format(point);
                    storeListDTO.setReviewAverage(dfAverage);

                    // 북마크 여부
                    boolean bookmarkYn = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
                    storeListDTO.setBookmark(bookmarkYn);

                    storeList.add(storeListDTO);
                }
            }
        }
        return storeList;
    }

    // 최근 방문한 맛집 목록
    public List<StoreListDTO> generateReviewStoreList(Pageable pageable, int userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<Review> reviewList = reviewBO.getReviewListByUserId(userId);

        List<StoreEntity> storeEntityList = getStoreList();

        Set<Integer> processedStoreIds = new HashSet<>();

        for (StoreEntity storeEntity : storeEntityList) {
            int storeId = storeEntity.getId();

            if (processedStoreIds.contains(storeId)) {
                continue;
            }

            for (Review review : reviewList) {
                if (review.getStoreId() == storeEntity.getId()) {
                    StoreListDTO storeListDTO = new StoreListDTO();

                    // 맛집 1개
                    storeListDTO.setStore(storeEntity);
                    storeId = storeEntity.getId();

                    // 메뉴 대표 이미지
                    if (menuBO.getMenuByStoreId(storeId) != null) {
                        String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                        storeListDTO.setMenuImage(imagePath);
                    }

                    // 리뷰 평점
                    double point = reviewBO.getReviewPointByStoreId(storeId);
                    DecimalFormat df = new DecimalFormat("#.#");
                    String dfAverage = df.format(point);
                    storeListDTO.setReviewAverage(dfAverage);

                    // 북마크 여부
                    boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
                    storeListDTO.setBookmark(bookmark);

                    storeList.add(storeListDTO);

                    processedStoreIds.add(storeId);
                    break;
                }
            }
        }
        return storeList;
    }

    // 단골 맛집 목록
    public List<StoreListDTO> generateRegularStoreList(Pageable pageable, int userId) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<Review> reviewList = reviewBO.getReviewListByUserId(userId);

        List<StoreEntity> storeEntityList = getStoreList();

        Map<Integer, Integer> reviewCountMap = new HashMap<>();
        for (Review review : reviewList) {
            int storeId = review.getStoreId();
            reviewCountMap.put(storeId, reviewCountMap.getOrDefault(storeId, 0) + 1);
        }

        Set<Integer> validStoreIds = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : reviewCountMap.entrySet()) {
            if (entry.getValue() >= 3) {
                validStoreIds.add(entry.getKey());
            }
        }

        for (StoreEntity storeEntity : storeEntityList) {
            int storeId = storeEntity.getId();

            if (!validStoreIds.contains(storeId)) {
                continue;
            }

            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeEntity);

            // 메뉴 대표 이미지
            if (menuBO.getMenuByStoreId(storeId) != null) {
                String imagePath = menuBO.getMenuByStoreId(storeId).getImagePath();
                storeListDTO.setMenuImage(imagePath);
            }

            // 리뷰 평점
            double point = reviewBO.getReviewPointByStoreId(storeId);
            DecimalFormat df = new DecimalFormat("#.#");
            String dfAverage = df.format(point);
            storeListDTO.setReviewAverage(dfAverage);

            // 북마크 여부
            boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
            storeListDTO.setBookmark(bookmark);

            storeList.add(storeListDTO);
        }
        return storeList;
    }

    public StoreDetailDTO generateStoreByStoreId(int storeId, Integer userId) {
        StoreDetailDTO storeDetailDTO = new StoreDetailDTO();

        // 맛집 정보
        StoreEntity store = getStoreById(storeId);
        storeDetailDTO.setStore(store);

        // 메뉴
        List<Menu> menuList = menuBO.getMenuListByStoreId(storeId);
        storeDetailDTO.setMenuList(menuList);

        // 리뷰
        List<Review> reviewList = reviewBO.getReviewListByStoreId(storeId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(review.getId());
            reviewDTO.setStoreId(review.getStoreId());
            reviewDTO.setUserId(review.getUserId());
            reviewDTO.setImagePath(review.getImagePath());
            reviewDTO.setPoint(review.getPoint());
            reviewDTO.setContent(review.getContent());
            reviewDTO.setCreatedAt(review.getCreatedAt());
            reviewDTO.setUpdatedAt(review.getUpdatedAt());

            UserEntity user = userBO.getUserEntityById(review.getUserId());
            reviewDTO.setUserName(user.getName());

            reviewDTOList.add(reviewDTO);
        }
        storeDetailDTO.setReviewList(reviewDTOList);

        // 리뷰 평점
        double point = reviewBO.getReviewPointByStoreId(storeId);
        DecimalFormat df = new DecimalFormat("#.#");
        String dfAverage = df.format(point);
        storeDetailDTO.setReviewAverage(dfAverage);

        // 북마크 개수
        int bookmarkCount = bookmarkBO.getBookmarkCountByStoreId(storeId);
        storeDetailDTO.setBookmarkCount(bookmarkCount);

        // 북마크 여부
        if (userId != null) {
            boolean bookmark = bookmarkBO.getBookmarkByUserIdStoreId(userId, storeId);
            storeDetailDTO.setBookmark(bookmark);
        }

        return storeDetailDTO;
    }
}
