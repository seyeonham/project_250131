package com.project_250131.store;

import com.project_250131.continent.bo.ContinentBO;
import com.project_250131.continent.entity.ContinentEntity;
import com.project_250131.region.bo.RegionBO;
import com.project_250131.region.entity.RegionEntity;
import com.project_250131.store.bo.PageBO;
import com.project_250131.store.bo.StoreBO;
import com.project_250131.store.domain.Page;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/store")
@Controller
public class StoreController {

    private final StoreBO storeBO;
    private final RegionBO regionBO;
    private final ContinentBO continentBO;
    private final PageBO pageBO;

    @GetMapping("/user-store-list")
    public String userStoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        String userRegion = (String)session.getAttribute("userRegion");
        Page storeListPage = null;
        int storeCount = 0;

        if (userId == null) {
            storeCount = storeBO.getStoreCount();
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeList = storeBO.generateStoreList(userId, storeListPage.getPageable());

            model.addAttribute("storeList", storeList);
        } else {
            storeCount = storeBO.getStoreCountByRegion(userRegion);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(storeListPage.getPageable(), userRegion, userId);


            model.addAttribute("region", userRegion);
            model.addAttribute("storeList", storeRegionList);
        }

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
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (region != null) {
            storeCount = storeBO.getStoreCountByRegion(region);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(storeListPage.getPageable(), region, userId);

            model.addAttribute("region", region);
            model.addAttribute("storeList", storeRegionList);

        } else if (continent != null) {
            storeCount = storeBO.getStoreCountByContinent(continent);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(storeListPage.getPageable(), continent, userId);

            model.addAttribute("continent", continent);
            model.addAttribute("storeList", storeContinentList);
        }

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
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Page storeListPage = null;
        int storeCount = 0;

        if (region != null) {
            storeCount = storeBO.getStoreCountByRegion(region);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(storeListPage.getPageable(), region, userId);

            model.addAttribute("region", region);
            model.addAttribute("storeList", storeRegionList);

        } else if (continent != null) {
            storeCount = storeBO.getStoreCountByContinent(continent);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(storeListPage.getPageable(), continent, userId);

            model.addAttribute("continent", continent);
            model.addAttribute("storeList", storeContinentList);

        } else if (name != null) {
            storeCount = storeBO.getStoreCountByStoreName(name);
            storeListPage = pageBO.storeListPage(pageable, page, storeCount);
            List<StoreListDTO> storeNameList = storeBO.generateStoreListByStoreName(storeListPage.getPageable(), name, userId);

            model.addAttribute("name", name);
            model.addAttribute("storeList", storeNameList);
        }

        model.addAttribute("storeCount", storeCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("storeListPage", storeListPage);
        return "store/searchList";
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
