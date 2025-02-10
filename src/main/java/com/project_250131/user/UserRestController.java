package com.project_250131.user;

import com.project_250131.user.bo.UserBO;
import com.project_250131.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserRestController {

    private final UserBO userBO;

    /**
     * 아이디 중복 확인
     * @param loginId
     * @return
     */
    @GetMapping("/is-duplicate-id")
    public Map<String, Object> isDuplicateId(
            @RequestParam("loginId") String loginId
    ) {
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        boolean isDuplicateId = false;

        // DB select
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            isDuplicateId = true;
        }

        // 응답값
        result.put("code", 200);
        result.put("is_duplicate_id", isDuplicateId);
        return result;
    }

    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("region") String region
    ) {
        // DB insert
        boolean isSuccess = userBO.addUserEntity(loginId, password, name, email, region);

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if (isSuccess) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "회원가입에 실패하였습니다.");
        }

        return result;
    }

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpSession session
    ) {
        // DB select
        UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLoginId", user.getLoginId());
            session.setAttribute("userName", user.getName());

            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 300);
            result.put("error_message", "아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        return result;
    }
}
