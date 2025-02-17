package com.project_250131.continent;

import com.project_250131.continent.bo.ContinentBO;
import com.project_250131.continent.entity.ContinentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/continent")
@Controller
public class ContinentController {

    private final ContinentBO continentBO;

    @GetMapping("/continent-list")
    public String continentList(
            Model model
    ) {
        List<ContinentEntity> continentList = continentBO.getContinentEntityList();
        model.addAttribute("continentList", continentList);
        return "continent/continentList";
    }
}
