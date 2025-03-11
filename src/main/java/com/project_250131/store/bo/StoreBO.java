package com.project_250131.store.bo;

import com.project_250131.bookmark.bo.BookmarkBO;
import com.project_250131.bookmark.domain.Bookmark;
import com.project_250131.menu.bo.MenuBO;
import com.project_250131.menu.domain.Menu;
import com.project_250131.review.bo.ReviewBO;
import com.project_250131.review.domain.Review;
import com.project_250131.review.domain.ReviewDTO;
import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import com.project_250131.user.bo.UserBO;
import com.project_250131.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreBO {

    private final StoreRepository storeRepository;
    private final MenuBO menuBO;
    private final ReviewBO reviewBO;
    private final BookmarkBO bookmarkBO;
    private final UserBO userBO;

    public Page<StoreDTO> getStoreList(Pageable pageable, String sort) {

        List<StoreEntity> storeEntities = storeRepository.findAll();

        List<StoreDTO> storeDTOList = storeEntities.stream().map(storeEntity -> {
            int storeId = storeEntity.getId();
            double reviewAverage = reviewBO.getReviewPointByStoreId(storeId);
            int reviewCount = reviewBO.getReviewCountByStoreId(storeId);
            return new StoreDTO(storeEntity, reviewAverage, reviewCount);
        }).collect(Collectors.toList());

        if (sort != null) {
            if (sort.equals("rating")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewAverage).reversed()); // 별점 높은 순
            } else if (sort.equals("review")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewCount).reversed()); // 리뷰 많은 순
            } else {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
            }
        } else {
            storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeDTOList.size());
        List<StoreDTO> pagedList = storeDTOList.subList(start, end);

        return new PageImpl<>(pagedList, pageable, storeDTOList.size());

    }

    public List<StoreDTO> getStoreList(String sort) {
        List<StoreEntity> storeEntities = storeRepository.findAll();

        List<StoreDTO> storeDTOList = storeEntities.stream().map(storeEntity -> {
            int storeId = storeEntity.getId();
            double reviewAverage = reviewBO.getReviewPointByStoreId(storeId);
            int reviewCount = reviewBO.getReviewCountByStoreId(storeId);
            return new StoreDTO(storeEntity, reviewAverage, reviewCount);
        }).collect(Collectors.toList());

        if (sort != null) {
            if (sort.equals("rating")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewAverage).reversed()); // 별점 높은 순
            } else if (sort.equals("review")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewCount).reversed()); // 리뷰 많은 순
            } else {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
            }
        } else {
            storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
        }

        return storeDTOList;
    }

    public int getStoreCount() {
        return (int) storeRepository.count();
    }

    public StoreEntity getStoreById(int id) {
        Optional<StoreEntity> store = storeRepository.findById(id);
        return store.orElse(null);
    }

    public Page<StoreDTO> getStoreListByRegion(String region, Pageable pageable, String sort) {

        List<StoreEntity> storeEntities = storeRepository
                .findByRoadAddressContainingOrStreetAddressContaining(region, region);

        List<StoreDTO> storeDTOList = storeEntities.stream().map(storeEntity -> {
            int storeId = storeEntity.getId();
            double reviewAverage = reviewBO.getReviewPointByStoreId(storeId);
            int reviewCount = reviewBO.getReviewCountByStoreId(storeId);
            return new StoreDTO(storeEntity, reviewAverage, reviewCount);
        }).collect(Collectors.toList());

        if (sort != null) {
            if (sort.equals("rating")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewAverage).reversed()); // 별점 높은 순
            } else if (sort.equals("review")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewCount).reversed()); // 리뷰 많은 순
            } else {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
            }
        } else {
            storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
        }


        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeDTOList.size());
        List<StoreDTO> pagedList = storeDTOList.subList(start, end);

        return new PageImpl<>(pagedList, pageable, storeDTOList.size());
    }

    public int getStoreCountByRegion(String region) {
        int count = storeRepository
                .countByRoadAddressContainingOrStreetAddressContaining(region, region);
        return count;
    }

    public Page<StoreDTO> getStoreListByContinent(String continent, Pageable pageable, String sort) {
        List<StoreEntity> storeEntities = storeRepository.findByContinentContaining(continent);

        List<StoreDTO> storeDTOList = storeEntities.stream().map(storeEntity -> {
            int storeId = storeEntity.getId();
            double reviewAverage = reviewBO.getReviewPointByStoreId(storeId);
            int reviewCount = reviewBO.getReviewCountByStoreId(storeId);
            return new StoreDTO(storeEntity, reviewAverage, reviewCount);
        }).collect(Collectors.toList());

        if (sort != null) {
            if (sort.equals("rating")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewAverage).reversed()); // 별점 높은 순
            } else if (sort.equals("review")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewCount).reversed()); // 리뷰 많은 순
            } else {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
            }
        } else {
            storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeDTOList.size());
        List<StoreDTO> pagedList = storeDTOList.subList(start, end);

        return new PageImpl<>(pagedList, pageable, storeDTOList.size());
    }

    public int getStoreCountByContinent(String continent) {
        int count = storeRepository.countByContinentContaining(continent);
        return count;
    }

    public Page<StoreDTO> getStoreListByStoreName(String name, Pageable pageable, String sort) {
        List<StoreEntity> storeEntities = storeRepository.findByStoreNameContaining(name);

        List<StoreDTO> storeDTOList = storeEntities.stream().map(storeEntity -> {
            int storeId = storeEntity.getId();
            double reviewAverage = reviewBO.getReviewPointByStoreId(storeId);
            int reviewCount = reviewBO.getReviewCountByStoreId(storeId);
            return new StoreDTO(storeEntity, reviewAverage, reviewCount);
        }).collect(Collectors.toList());

        if (sort != null) {
            if (sort.equals("rating")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewAverage).reversed()); // 별점 높은 순
            } else if (sort.equals("review")) {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getReviewCount).reversed()); // 리뷰 많은 순
            } else {
                storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
            }
        } else {
            storeDTOList.sort(Comparator.comparing(StoreDTO::getId));
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeDTOList.size());
        List<StoreDTO> pagedList = storeDTOList.subList(start, end);

        return new PageImpl<>(pagedList, pageable, storeDTOList.size());
    }

    public int getStoreCountByStoreName(String name) {
        int count = storeRepository.countByStoreNameContaining(name);
        return count;
    }

    // 전체 맛집 목록
    public List<StoreListDTO> generateStoreList(Integer userId, Pageable pageable, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreDTO> stores = getStoreList(pageable, sort);

        for (StoreDTO storeDTO : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeDTO);
            int storeId = storeDTO.getId();

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
    public List<StoreListDTO> generateStoreListByRegion(Pageable pageable, String region, Integer userId, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreDTO> stores = getStoreListByRegion(region, pageable, sort);

        for (StoreDTO storeDTO : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeDTO);
            int storeId = storeDTO.getId();

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
    public List<StoreListDTO> generateStoreListByContinent(Pageable pageable, String continent, Integer userId, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreDTO> stores = getStoreListByContinent(continent, pageable, sort);

        for (StoreDTO storeDTO : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeDTO);
            int storeId = storeDTO.getId();

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
    public List<StoreListDTO> generateStoreListByStoreName(Pageable pageable, String name, Integer userId, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        Page<StoreDTO> stores = getStoreListByStoreName(name, pageable, sort);

        for (StoreDTO storeDTO : stores) {
            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeDTO);
            int storeId = storeDTO.getId();

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
    public List<StoreListDTO> generateBookmarkStoreList(Pageable pageable, int userId, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<Bookmark> bookmarkList = bookmarkBO.getBookmarkListByUserIdDeleteYn(userId);

        List<StoreDTO> storeDTOList = getStoreList(sort);

        for (StoreDTO storeDTO : storeDTOList) {
            StoreListDTO storeListDTO = new StoreListDTO();

            for (Bookmark bookmark : bookmarkList) {
                if (bookmark.getStoreId() == storeDTO.getId()) {

                    // 맛집 1개
                    storeListDTO.setStore(storeDTO);
                    int storeId = storeDTO.getId();

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
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeList.size());
        return storeList.subList(start, end);
    }

    // 최근 방문한 맛집 목록
    public List<StoreListDTO> generateReviewStoreList(Pageable pageable, int userId, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<Review> reviewList = reviewBO.getReviewListByUserId(userId);

        List<StoreDTO> storeDTOList = getStoreList(sort);

        Set<Integer> processedStoreIds = new HashSet<>();

        for (StoreDTO storeDTO : storeDTOList) {
            int storeId = storeDTO.getId();

            if (processedStoreIds.contains(storeId)) {
                continue;
            }

            StoreListDTO storeListDTO = new StoreListDTO();

            for (Review review : reviewList) {
                if (storeId == review.getStoreId()) {

                    // 맛집 1개
                    storeListDTO.setStore(storeDTO);

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
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeList.size());
        return storeList.subList(start, end);
    }

    // 단골 맛집 목록
    public List<StoreListDTO> generateRegularStoreList(Pageable pageable, int userId, String sort) {
        List<StoreListDTO> storeList = new ArrayList<>();

        List<Review> reviewList = reviewBO.getReviewListByUserId(userId);

        List<StoreDTO> storeDTOList = getStoreList(sort);

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

        for (StoreDTO storeDTO : storeDTOList) {
            int storeId = storeDTO.getId();

            if (!validStoreIds.contains(storeId)) {
                continue;
            }

            StoreListDTO storeListDTO = new StoreListDTO();

            // 맛집 1개
            storeListDTO.setStore(storeDTO);

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
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeList.size());
        return storeList.subList(start, end);
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
