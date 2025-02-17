package com.project_250131.user;

import com.project_250131.user.bo.MailService;
import com.project_250131.user.bo.UserBO;
import com.project_250131.user.entity.UserEntity;
import jakarta.mail.MessagingException;
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
    private final MailService mailService;

    /**
     * 아이디 중복 확인
     * @param loginId
     * @return
     */
    @GetMapping("/is-duplicate-id")
    public Map<String, Object> isDuplicateId(
            @RequestParam("loginId") String loginId,
            HttpSession session
    ) {
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        boolean isDuplicateId = false;
        boolean isSame = false;

        // DB select
        Map<String, Object> result = new HashMap<>();
        Integer userId = (Integer)session.getAttribute("userId");
        if (user != null) {
            UserEntity sessionUser = userBO.getUserEntityById(userId);
            if (sessionUser.getLoginId().equals(loginId)) {
                isSame = true;
            } else {
                isDuplicateId = true;
            }
        }

        // 응답값
        result.put("code", 200);
        result.put("is_duplicate_id", isDuplicateId);
        result.put("is_same", isSame);
        return result;
    }

    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam(value = "region", required = false) String region
    ) {
        // DB insert
        UserEntity user = userBO.addUserEntity(loginId, password, name, email, region, "local");

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
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
            session.setAttribute("userProvider", user.getProvider());

            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 300);
            result.put("error_message", "아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        return result;
    }

    @GetMapping("/check-password")
    public Map<String, Object> checkPassword(
            @RequestParam("password") String password,
            HttpSession session
    ) {
        String loginId = (String)session.getAttribute("userLoginId");

        UserEntity userEntity = userBO.getUserEntityByLoginIdPassword(loginId, password);
        Map<String, Object> result = new HashMap<>();
        boolean checkPassword = false;
        if (userEntity != null) {
            checkPassword = true;
        }

        result.put("code", 200);
        result.put("check_password", checkPassword);
        return result;
    }

    @PatchMapping("/edit-profile")
    public Map<String, Object> editProfile (
            @RequestParam(value = "loginId", required = false) String loginId,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "region", required = false) String region,
            HttpSession session
    ) {

        int userId = (Integer)session.getAttribute("userId");
        String provider = (String)session.getAttribute("userProvider");

        UserEntity userEntity = userBO.updateUserEntityById(userId, loginId, newPassword, name, email, region, provider);
        Map<String, Object> result = new HashMap<>();
        if (userEntity != null) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "정보를 수정하는데 실패했습니다.");
        }
        return result;
    }

    @PostMapping("/find-loginId")
    public Map<String, Object> findLoginId(
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {

        UserEntity userEntity = userBO.getUserEntityByNameEmail(name, email);

        Map<String, Object> result = new HashMap<>();
        if (userEntity != null) {
            result.put("code", 200);
            result.put("result", "성공");
            result.put("loginId", userEntity.getLoginId());
        } else {
            result.put("code", 300);
        }

        return result;
    }

    @PostMapping("/find-password")
    public Map<String, Object> findPassword(
            @RequestParam("loginId") String loginId,
            @RequestParam("email") String email
    ) {
        UserEntity userEntity = userBO.getUserEntityByLoginIdEmail(loginId, email);

        Map<String, Object> result = new HashMap<>();
        if (userEntity != null) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 300);
        }

        return result;
    }

    @GetMapping("/send-passcode")
    public Map<String, Object> sendPasscode(
            @RequestParam("email") String email
    ) {

        Map<String, Object> result = new HashMap<>();
        try {
            int passcode = mailService.sendMail(email);
            result.put("code", 200);
            result.put("passcode", passcode);
        } catch (MessagingException e) {
            result.put("code", 500);
            throw new RuntimeException(e);
        }

        return result;
    }

    @PatchMapping("/change-password")
    public Map<String, Object> changePassword(
            @RequestParam("loginId") String loginId,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        UserEntity userEntity = userBO.updateUserEntityByLoginIdEmail(loginId, email, password);

        Map<String, Object> result = new HashMap<>();
        if (userEntity != null) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "비밀번호 변경에 실패했습니다.");
        }

        return result;
    }
}
