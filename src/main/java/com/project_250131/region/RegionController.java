package com.project_250131.region;

import com.project_250131.region.bo.RegionBO;
import com.project_250131.region.entity.RegionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/region")
@Controller
public class RegionController {

    private final RegionBO regionBO;

    @GetMapping("/region-list")
    public String choiceList(
            Model model
    ) {
        List<RegionEntity> regionList = regionBO.getRegionList();
        model.addAttribute("regionList", regionList);
        return "region/regionList";
    }
}
