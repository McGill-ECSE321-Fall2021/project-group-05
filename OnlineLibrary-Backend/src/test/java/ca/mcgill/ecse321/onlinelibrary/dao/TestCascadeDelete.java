package ca.mcgill.ecse321.onlinelibrary.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.onlinelibrary.model.Child;
import ca.mcgill.ecse321.onlinelibrary.model.Parent;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestCascadeDelete {

	@Autowired
	private ParentRepository parentRepository;

	@AfterEach
	public void clearDatabase() {
		parentRepository.deleteAll();
	}

	@Test
	public void testCascadeDelete() {
		Parent p = new Parent();
		Child c = new Child();
		p.setChild(c);
		c.setParent(p);

		parentRepository.save(p);
	}
}
