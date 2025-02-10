package com.project_250131;

import com.project_250131.user.bo.UserBO;
import com.project_250131.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class Project250131ApplicationTests {

	@Autowired
	UserBO userBO;

	// @Test
	void contextLoads() {
		log.warn("######### 테스트");
		int a = 10;
		int b = 20;

		assertEquals(30, a + b);
	}

	@Transactional
	@Test
	void 회원가입() {
		userBO.addUserEntity("test", "test", "name",
				"email@google.com", "서울특별시");
	}

	@Test
	void 로그인() {
		// given
		String loginId = "bbbb";
		String password = "bbbb";

		// when
		UserEntity result = userBO.getUserEntityByLoginIdPassword(loginId, password);

		// then
		assertEquals(loginId, result.getLoginId());
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		assertTrue(passwordEncoder.matches(password, result.getPassword()));
	}

}
