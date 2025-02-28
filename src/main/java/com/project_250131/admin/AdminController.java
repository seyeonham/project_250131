package com.project_250131.admin;

import com.project_250131.continent.bo.ContinentBO;
import com.project_250131.continent.entity.ContinentEntity;
import com.project_250131.store.bo.StoreBO;
import com.project_250131.store.domain.StoreDetailDTO;
import com.project_250131.store.domain.StoreListDTO;
import jakarta.mail.Store;
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
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final StoreBO storeBO;
    private final ContinentBO continentBO;

    @GetMapping("/store-list")
    public String StoreList(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model
    ) {
        Pageable updatedPageable = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());

        int currentGroup = (int) Math.ceil((double) page / 10);
        int startPage = 0;
        int totalPages = 0;
        int endPage = 0;

        if (keyword == null) {
            List<StoreListDTO> storeList = storeBO.generateStoreList(null, updatedPageable);
            long storeCount = storeBO.getStoreCount();

            if (storeCount > 0) {
                totalPages = (int) Math.ceil((double) storeCount / pageable.getPageSize());
                startPage = (currentGroup - 1) * 10 + 1;
                endPage = Math.min(startPage + 9, totalPages);
            }

            model.addAttribute("storeList", storeList);
            model.addAttribute("storeCount", storeCount);
        } else {
            List<StoreListDTO> storeNameList = storeBO.generateStoreListByStoreName(updatedPageable, keyword, null);
            long storeCount = storeBO.getStoreCountByStoreName(keyword);

            if (storeCount > 0) {
                totalPages = (int) Math.ceil((double) storeCount / pageable.getPageSize());
                startPage = (currentGroup - 1) * 10 + 1;
                endPage = Math.min(startPage + 9, totalPages);
            }

            model.addAttribute("storeList", storeNameList);
            model.addAttribute("storeCount", storeCount);
        }

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageable", updatedPageable);
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("startPage", startPage);
        return "admin/StoreList";
    }

    @GetMapping("store-detail")
    public String storeDetail(
            @RequestParam("storeId") int storeId,
            Model model
    ) {
        StoreDetailDTO storeList = storeBO.generateStoreByStoreId(storeId, null);
        model.addAttribute("storeList", storeList);

        return "admin/storeDetail";
    }

    @GetMapping("/add-store")
    public String addStore(Model model) {

        List<ContinentEntity> continentList = continentBO.getContinentEntityList();
        model.addAttribute("continentList", continentList);
        return "admin/addStore";
    }
}
