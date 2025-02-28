package com.project_250131.store;

import com.project_250131.continent.bo.ContinentBO;
import com.project_250131.continent.entity.ContinentEntity;
import com.project_250131.region.bo.RegionBO;
import com.project_250131.region.entity.RegionEntity;
import com.project_250131.store.bo.StoreBO;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import com.project_250131.store.entity.StoreEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/user-store-list")
    public String userStoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model, HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        String userRegion = (String)session.getAttribute("userRegion");

        Pageable updatedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        int currentGroup = (int) Math.ceil((double) page / 10);
        int startPage = (currentGroup - 1) * 10 + 1;

        if (userId == null) {
            List<StoreListDTO> storeList = storeBO.generateStoreList(userId, pageable);
            long storeCount = storeBO.getStoreCount();
            int totalPages = (int) Math.ceil((double) storeCount / pageable.getPageSize());
            int endPage = Math.min(startPage + 9, totalPages);
            model.addAttribute("storeList", storeList);
            model.addAttribute("storeCount", storeCount);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("endPage", endPage);
        } else {
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(updatedPageable, userRegion, userId);
            int storeRegionCount = storeBO.getStoreCountByRegion(userRegion);
            List<ContinentEntity> continentList = continentBO.getContinentEntityList();

            int totalPages = (int) Math.ceil((double) storeRegionCount / pageable.getPageSize());
            int endPage = Math.min(startPage + 9, totalPages);

            model.addAttribute("region", userRegion);
            model.addAttribute("continentList", continentList);
            model.addAttribute("storeRegionList", storeRegionList);
            model.addAttribute("storeRegionCount", storeRegionCount);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("endPage", endPage);
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("pageable", updatedPageable);
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("startPage", startPage);
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

        Pageable updatedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        int currentGroup = (int) Math.ceil((double) page / 10);
        int startPage = (currentGroup - 1) * 10 + 1;

        if (region != null) {

            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(updatedPageable, region, userId);
            int storeRegionCount = storeBO.getStoreCountByRegion(region);
            List<ContinentEntity> continentList = continentBO.getContinentEntityList();

            int totalPages = (int) Math.ceil((double) storeRegionCount / pageable.getPageSize());
            int endPage = Math.min(startPage + 9, totalPages);

            model.addAttribute("region", region);
            model.addAttribute("continentList", continentList);
            model.addAttribute("storeRegionList", storeRegionList);
            model.addAttribute("storeRegionCount", storeRegionCount);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("endPage", endPage);

        } else if (continent != null) {
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(updatedPageable, continent, userId);
            int storeContinentCount = storeBO.getStoreCountByContinent(continent);
            List<RegionEntity> regionList = regionBO.getRegionList();

            int totalPages = (int) Math.ceil((double) storeContinentCount / pageable.getPageSize());
            int endPage = Math.min(startPage + 9, totalPages);

            model.addAttribute("continent", continent);
            model.addAttribute("regionList", regionList);
            model.addAttribute("storeContinentList", storeContinentList);
            model.addAttribute("storeContinentCount", storeContinentCount);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("endPage", endPage);
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("pageable", updatedPageable);
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("startPage", startPage);
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

        Pageable updatedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        int currentGroup = (int) Math.ceil((double) page / 10);
        int startPage = 0;
        int totalPages = 0;
        int endPage = 0;

        if (region != null) {

            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(updatedPageable, region, userId);
            int storeRegionCount = storeBO.getStoreCountByRegion(region);
            List<ContinentEntity> continentList = continentBO.getContinentEntityList();

            if (storeRegionCount > 0) {
                totalPages = (int) Math.ceil((double) storeRegionCount / pageable.getPageSize());
                startPage = (currentGroup - 1) * 10 + 1;
                endPage = Math.min(startPage + 9, totalPages);
            }

            model.addAttribute("region", region);
            model.addAttribute("continentList", continentList);
            model.addAttribute("storeRegionList", storeRegionList);
            model.addAttribute("storeRegionCount", storeRegionCount);

        } else if (continent != null) {
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(updatedPageable, continent, userId);
            int storeContinentCount = storeBO.getStoreCountByContinent(continent);
            List<RegionEntity> regionList = regionBO.getRegionList();

            if (storeContinentCount > 0) {
                totalPages = (int) Math.ceil((double) storeContinentCount / pageable.getPageSize());
                startPage = (currentGroup - 1) * 10 + 1;
                endPage = Math.min(startPage + 9, totalPages);
            }

            model.addAttribute("continent", continent);
            model.addAttribute("regionList", regionList);
            model.addAttribute("storeContinentList", storeContinentList);
            model.addAttribute("storeContinentCount", storeContinentCount);
        } else if (name != null) {
            List<StoreListDTO> storeNameList = storeBO.generateStoreListByStoreName(updatedPageable, name, userId);
            int storeNameCount = storeBO.getStoreCountByStoreName(name);

            if (storeNameCount > 0) {
                totalPages = (int) Math.ceil((double) storeNameCount / pageable.getPageSize());
                startPage = (currentGroup - 1) * 10 + 1;
                endPage = Math.min(startPage + 9, totalPages);
            }

            model.addAttribute("name", name);
            model.addAttribute("storeNameList", storeNameList);
            model.addAttribute("storeNameCount", storeNameCount);
        }

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageable", updatedPageable);
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("startPage", startPage);
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
