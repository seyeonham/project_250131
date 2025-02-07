package com.project_250131.user.bo;

import com.project_250131.user.entity.UserEntity;
import com.project_250131.user.mapper.UserMapper;
import com.project_250131.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
}
