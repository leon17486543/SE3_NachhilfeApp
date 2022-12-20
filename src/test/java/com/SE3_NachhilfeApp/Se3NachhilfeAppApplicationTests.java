package com.SE3_NachhilfeApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Se3NachhilfeAppApplicationTests {

	@Autowired private Se3NachhilfeAppApplication application;

	@Test
	void contextLoads(){
		assertThat(application).isNotNull();
	}

}
