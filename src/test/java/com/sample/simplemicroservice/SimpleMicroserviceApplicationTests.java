package com.sample.simplemicroservice;

import com.sample.simplemicroservice.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class SimpleMicroserviceApplicationTests {

	@Autowired // You can now inject your repository
	private DepartmentRepository departmentRepository;

	@Test
	void contextLoads() {

	}

}
