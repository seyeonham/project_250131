package com.project_250131.user.bo;

import com.project_250131.common.EncryptUtils;
import com.project_250131.user.entity.UserEntity;
import com.project_250131.user.mapper.UserMapper;
import com.project_250131.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public UserEntity getUserEntityByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        return userEntity;
    }

    // 유저 한 명 가져오기
    public UserEntity getUserEntityById(int id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return userEntity;
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

    // 아이디 찾기
    public UserEntity getUserEntityByNameEmail(String name, String email) {
        UserEntity userEntity = userRepository.findByNameAndEmail(name, email).orElse(null);

        return userEntity;
    }

    // 회원가입
    public UserEntity addUserEntity(String loginId, String password, String name,
                                String email, String region, String provider) {

        String hashedPassword = null;
        if (password != null) {
            hashedPassword = EncryptUtils.bcrypt(password);
        }

        UserEntity user = userRepository.save(
            UserEntity.builder()
                    .loginId(loginId)
                    .password(hashedPassword)
                    .name(name)
                    .email(email)
                    .region(region)
                    .provider(provider)
                    .build()
        );

        return user;
    }

    // 유저 정보 수정
    public UserEntity updateUserEntityById(int userId, String loginId, String newPassword, String name,
                                           String email, String region, String provider) {

        Optional<UserEntity> user = userRepository.findById(userId);
        String hashedPassword = "";
        if (newPassword != "") {
            hashedPassword = EncryptUtils.bcrypt(newPassword);
        }

        if (provider == "local") {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                userEntity = userEntity.builder()
                        .id(userId)
                        .loginId(loginId != "" ? loginId : userEntity.getLoginId())
                        .password(hashedPassword != "" ? hashedPassword : userEntity.getPassword())
                        .name(name != "" ? name : userEntity.getName())
                        .email(email != "" ? email : userEntity.getEmail())
                        .region(region != null ? region : userEntity.getRegion())
                        .provider(provider)
                        .createdAt(userEntity.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .build();

                return userRepository.save(userEntity);
            }
        } else {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                userEntity = userEntity.builder()
                        .id(userId)
                        .loginId(loginId)
                        .password(hashedPassword)
                        .name(name != "" ? name : userEntity.getName())
                        .email(email != "" ? email : userEntity.getEmail())
                        .region(region != null ? region : userEntity.getRegion())
                        .provider(provider)
                        .createdAt(userEntity.getCreatedAt())
                        .updatedAt(LocalDateTime.now())
                        .build();

                return userRepository.save(userEntity);
            }
        }
        return null;
    }
}
