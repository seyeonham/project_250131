package com.project_250131;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
//@SpringBootTest
class Project250131ApplicationTests {

	@Test
	void contextLoads() {
		log.warn("######### 테스트");
		int a = 10;
		int b = 20;

		assertEquals(30, a + b);
	}

}
