package by.epam.periodicals_site.service.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import by.epam.periodicials_site.entity.Role;
import by.epam.periodicials_site.entity.User;
import by.epam.periodicials_site.service.util.Validator;

@RunWith(Parameterized.class)
public class UserDataValidTest {
	
	public UserDataValidTest(User user) {
		this.user = user;
	}
	
	private User user;

	@Parameters
	public static List<User[]> dataForTest(){
		return Arrays.asList(new User[][] {
			{new User(0, "VlaSV", "password", "Vladislav", "Sevashko", "email@email.com", 0.0, Role.CUSTOMER)},
			{new User(0, "IvanSV", "pass", "Ivan", "Ivanov", "email@email.com", 1.0, Role.CUSTOMER)},
			{new User(0, "VlaSV", "qwerrty", "Vladislav", "Abramafa", "email@email.com", 5.0, Role.CUSTOMER)},
			{new User(0, "VlaSV", "password", "raaaaaaav", "Sevashko", "email@email.com", 5.0, Role.CUSTOMER)}
		});
	}

	@Test
	public void userIsValid() {
		assertTrue(Validator.userIsValid(user));
	}
}
