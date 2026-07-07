package com.hexaware.hotbyte;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HotbyteApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("BCRYPT HASH: " + new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("HotByte123"));
	}

}
