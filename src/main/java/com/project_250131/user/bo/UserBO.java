package com.project_250131.user.bo;

import com.project_250131.common.EncryptUtils;
import com.project_250131.user.entity.UserEntity;
import com.project_250131.user.mapper.UserMapper;
import com.project_250131.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserBO {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<Map<String, Object>> getUserListTest() {
        return userMapper.selectUserListTest();
    }

    // 아이디 중복 확인
    public UserEntity getUserEntityByLoginId(String loginId) {
        UserEntity userEntity = userRepository.findByLoginId(loginId).orElse(null);
        return userEntity;
    }

    // 회원가입
    public boolean addUserEntity(String loginId, String password, String name,
                                String email, String region) {

        String hashedPassword = EncryptUtils.bcrypt(password);

        UserEntity user = userRepository.save(
            UserEntity.builder()
                    .loginId(loginId)
                    .password(hashedPassword)
                    .name(name)
                    .email(email)
                    .region(region)
                    .build()
        );

        return user == null ? false : true;
    }

    public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {

        UserEntity userEntity = userRepository.findByLoginId(loginId).orElse(null);
        if (userEntity != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }

        return null;
    }
}
