package com.project_250131.store;

import com.project_250131.bookmark.bo.BookmarkBO;
import com.project_250131.review.bo.ReviewBO;
import com.project_250131.review.domain.Review;
import com.project_250131.store.bo.PageBO;
import com.project_250131.store.bo.StoreBO;
import com.project_250131.store.domain.Page;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/store")
@Controller
public class StoreController {

    private final StoreBO storeBO;
    private final PageBO pageBO;
    private final BookmarkBO bookmarkBO;
    private final ReviewBO reviewBO;

    @GetMapping("/user-store-list")
    public String userStoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sorting", defaultValue = "default") String sorting,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        String userRegion = (String)session.getAttribute("userRegion");
        Page storeListPage = null;
        int storeCount = 0;

        if (userId == null) {
            storeCount = storeBO.getStoreCount();
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeList = storeBO.generateStoreList(userId, storeListPage.getPageable(), sorting);

            model.addAttribute("storeList", storeList);
        } else {
            storeCount = storeBO.getStoreCountByRegion(userRegion);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(storeListPage.getPageable(), userRegion, userId, sorting);


            model.addAttribute("region", userRegion);
            model.addAttribute("storeList", storeRegionList);
        }

        model.addAttribute("sorting", sorting);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/userStoreList";
    }

    @GetMapping("/store-list")
    public String storeList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sorting", required = false) String sorting,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (region != null) {
            storeCount = storeBO.getStoreCountByRegion(region);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(storeListPage.getPageable(), region, userId, sorting);

            model.addAttribute("region", region);
            model.addAttribute("storeList", storeRegionList);

        } else if (continent != null) {
            storeCount = storeBO.getStoreCountByContinent(continent);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(storeListPage.getPageable(), continent, userId, sorting);

            model.addAttribute("continent", continent);
            model.addAttribute("storeList", storeContinentList);
        }

        model.addAttribute("sorting", sorting);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/storeList";
    }

    @GetMapping("/search-list")
    public String searchList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "continent", required = false) String continent,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sorting", required = false) String sorting,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (region != null) {
            storeCount = storeBO.getStoreCountByRegion(region);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(storeListPage.getPageable(), region, userId, sorting);

            model.addAttribute("region", region);
            model.addAttribute("storeList", storeRegionList);

        } else if (continent != null) {
            storeCount = storeBO.getStoreCountByContinent(continent);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(storeListPage.getPageable(), continent, userId, sorting);

            model.addAttribute("continent", continent);
            model.addAttribute("storeList", storeContinentList);

        } else if (name != null) {
            storeCount = storeBO.getStoreCountByStoreName(name);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeNameList = storeBO.generateStoreListByStoreName(storeListPage.getPageable(), name, userId, sorting);

            model.addAttribute("name", name);
            model.addAttribute("storeList", storeNameList);
        }

        model.addAttribute("sorting", sorting);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/searchList";
    }

    @GetMapping("/bookmark-store-list")
    public String bookmarkStoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sorting", required = false) String sorting,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (userId == null) {
            return "redirect:/user/sign-in";
        } else {
            storeCount = bookmarkBO.getBookmarkCountByUserIdDeleteYn(userId);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeList = storeBO.generateBookmarkStoreList(storeListPage.getPageable(), userId, sorting);

            model.addAttribute("storeList", storeList);
        }

        model.addAttribute("sorting", sorting);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/bookmarkStoreList";
    }

    @GetMapping("/visit-store-list")
    public String visitStoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sorting", required = false) String sorting,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (userId == null) {
            return "redirect:/user/sign-in";
        } else {
            storeCount = reviewBO.getReviewCountByUserId(userId);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeList = storeBO.generateReviewStoreList(storeListPage.getPageable(), userId, sorting);

            model.addAttribute("storeList", storeList);
        }

        model.addAttribute("sorting", sorting);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/visitStoreList";
    }

    @GetMapping("/regular-store-list")
    public String regularStoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sorting", required = false) String sorting,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (userId == null) {
            return "redirect:/user/sign-in";
        } else {
            storeCount = reviewBO.getRegularReviewCountByUserId(userId);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeList = storeBO.generateRegularStoreList(storeListPage.getPageable(), userId, sorting);

            model.addAttribute("storeList", storeList);
        }

        model.addAttribute("sorting", sorting);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/regularStoreList";
    }

    @GetMapping("/store-detail")
    public String storeDetail(
            @RequestParam("storeId") int storeId,
            Model model, HttpSession session
    ) {

        Integer userId = (Integer)session.getAttribute("userId");
        StoreDetailDTO storeList = storeBO.generateStoreByStoreId(storeId, userId);

        model.addAttribute("storeList", storeList);

        return "store/storeDetail";
    }

    @GetMapping("store-detail-menu")
    public String storeDetailMenu(
            @RequestParam("storeId") int storeId,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        StoreDetailDTO storeList = storeBO.generateStoreByStoreId(storeId, userId);
        model.addAttribute("storeList", storeList);
        return "store/storeDetailMenu";
    }

    @GetMapping("store-detail-review")
    public String storeDetailReview(
            @RequestParam("storeId") int storeId,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        StoreDetailDTO storeList = storeBO.generateStoreByStoreId(storeId, userId);

        List<Review> reviewList = reviewBO.getReviewListByStoreId(storeId);

        Map<Integer, Integer> reviewCountMap = new HashMap<>();
        List<Integer> regularReview = new ArrayList<>();
        for (Review review : reviewList) {
            int reviewUserId = review.getUserId();

            int count = reviewCountMap.getOrDefault(reviewUserId, 0) + 1;
            reviewCountMap.put(reviewUserId, count);

            if (count >= 3 && !regularReview.contains(reviewUserId)) {
                regularReview.add(reviewUserId);
            }
        }

        model.addAttribute("regularReview", regularReview);
        model.addAttribute("storeList", storeList);
        return "store/storeDetailReview";
    }

    @GetMapping("store-detail-info")
    public String storeDetailInfo(
            @RequestParam("storeId") int storeId,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        StoreDetailDTO storeList = storeBO.generateStoreByStoreId(storeId, userId);
        model.addAttribute("storeList", storeList);
        return "store/storeDetailInfo";
    }
}
