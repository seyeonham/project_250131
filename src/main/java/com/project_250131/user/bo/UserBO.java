package com.project_250131.user.bo;

import com.project_250131.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserBO {

    private final UserMapper userMapper;

    public List<Map<String, Object>> getUserListTest() {
        return userMapper.selectUserListTest();
    }
}
