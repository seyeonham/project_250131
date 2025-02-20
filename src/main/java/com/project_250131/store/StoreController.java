package com.project_250131.store;

import com.project_250131.continent.bo.ContinentBO;
import com.project_250131.continent.entity.ContinentEntity;
import com.project_250131.region.bo.RegionBO;
import com.project_250131.region.entity.RegionEntity;
import com.project_250131.store.bo.StoreBO;
import com.project_250131.store.domain.StoreListDTO;
import com.project_250131.store.entity.StoreEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    public String userStoreList() {
        return "store/userStoreList";
    }

    @GetMapping("/store-list")
    public String storeList(
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "continent", required = false) String continent,
            Model model, HttpSession session
    ) {
        int userId = (Integer)session.getAttribute("userId");

        if (region != null) {
            List<StoreListDTO> storeRegionList = storeBO.generateStoreListByRegion(region);
            int storeRegionCount = storeBO.getStoreCountByRegion(region);
            List<ContinentEntity> continentList = continentBO.getContinentEntityList();
            model.addAttribute("region", region);
            model.addAttribute("continentList", continentList);
            model.addAttribute("storeRegionList", storeRegionList);
            model.addAttribute("storeRegionCount", storeRegionCount);
            model.addAttribute("storeContinentList", null);
        } else if (continent != null) {
            List<StoreListDTO> storeContinentList = storeBO.generateStoreListByContinent(continent);
            int storeContinentCount = storeBO.getStoreCountByContinent(continent);
            List<RegionEntity> regionList = regionBO.getRegionList();
            model.addAttribute("continent", continent);
            model.addAttribute("regionList", regionList);
            model.addAttribute("storeContinentList", storeContinentList);
            model.addAttribute("storeContinentCount", storeContinentCount);
        }

        return "store/storeList";
    }

    @GetMapping("/search-list")
    public String searchList(
            @RequestParam(value = "regionKeyword", required = false) String regionKeyword,
            @RequestParam(value = "continentKeyword", required = false) String continentKeyword,
            Model model
    ) {
        if (regionKeyword != null) {
            List<StoreEntity> regionStoreList = storeBO.getStoreListByRegion(regionKeyword);
            model.addAttribute("regionStoreList", regionStoreList);
        }

        if (continentKeyword != null) {
            List<StoreEntity> continentStoreList = storeBO.getStoreListByContinent(continentKeyword);
            model.addAttribute("continentStoreList", continentStoreList);
        }

        return "store/searchList";
    }

    @GetMapping("/store-detail")
    public String storeDetail(
            @RequestParam("storeId") int storeId,
            Model model
    ) {


        return "store/storeDetail";
    }
}
