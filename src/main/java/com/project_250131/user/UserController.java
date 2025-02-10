package com.project_250131.user;

import com.project_250131.region.bo.RegionBO;
import com.project_250131.region.entity.RegionEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final RegionBO regionBO;

    @GetMapping("/sign-in")
    public String signIn() {
        return "user/signIn";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        List<RegionEntity> regionList = regionBO.getRegionList();

        model.addAttribute("regionList", regionList);
        return "user/signUp";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("userLoginId");
        session.removeAttribute("userName");

        return "redirect:/user/sign-in";
    }

    @GetMapping("/naver-login")
    public void naverLogin() {
        
    }
}
