package com.project_250131.user;

import com.project_250131.region.bo.RegionBO;
import com.project_250131.region.entity.RegionEntity;
import com.project_250131.user.bo.NaverLoginService;
import com.project_250131.user.bo.UserBO;
import com.project_250131.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final RegionBO regionBO;
    private final UserBO userBO;
    private final NaverLoginService naverLoginService;


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
    public void naverLogin(HttpServletResponse response) throws IOException {
        String clientId = "qOmR_kGT5NAw084OznZs";
        String redirectUri = URLEncoder.encode("http://localhost:80/user/naver/callback", "UTF-8");
        String state = UUID.randomUUID().toString(); // CSRF 방지를 위한 랜덤 값

        String naverLoginUrl = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&state=" + state;

        response.sendRedirect(naverLoginUrl);
    }

    @GetMapping("/naver/callback")
    public String naverCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpSession session
    ) {
        String accessToken = naverLoginService.getAccessToken(code, state);
        if (accessToken == null) {
            return "redirect:/user/sign-in";
        }

        Map<String, String> userInfo = naverLoginService.getUserInfo(accessToken);
        String name = userInfo.get("name");
        String email = userInfo.get("email");

        if (name == null || email == null) {
            return "redirect:/user/sign-in";
        }

        UserEntity user = userBO.getUserEntityByEmail(email);
        if (user == null) {
            user = userBO.addUserEntity(null, null, name, email, null, "naver");
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userProvider", user.getProvider());

        return "redirect:/store/user-store-list";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, HttpSession session) {
        List<RegionEntity> regionList = regionBO.getRegionList();
        model.addAttribute("regionList", regionList);

        int id = (Integer)session.getAttribute("userId");
        UserEntity user = userBO.getUserEntityById(id);
        model.addAttribute("user", user);

        return "user/editProfile";
    }

    @GetMapping("/find-loginId")
    public String findLoginId() {
        return "user/findLoginId";
    }


}
