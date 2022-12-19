package com.SE3_NachhilfeApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class Se3NachhilfeAppApplicationTests {

	@Autowired private Se3NachhilfeAppApplication application;

	@Test
	void contextLoads(){
		assertThat(application).isNotNull();
	}

}
