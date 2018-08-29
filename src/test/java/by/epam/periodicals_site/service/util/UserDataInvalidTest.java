package by.epam.periodicals_site.service.util;

import static org.junit.Assert.assertFalse;

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
public class UserDataInvalidTest {
	
	public UserDataInvalidTest(User user) {
		this.user = user;
	}
	
	private User user;

	@Parameters
	public static List<User[]> dataForTest(){
		return Arrays.asList(new User[][] {
			{new User(0, "", "password", "Vladislav", "Sevashko", "email@email.com", 0.0, Role.CUSTOMER)},
			{new User(0, "IvanSV", null, "Ivan", "Ivanov", "email@email.com", 1.0, Role.CUSTOMER)},
			{new User(0, "VlaSV", "password", "", "Sevashko", "emailemail.com", 5.0, Role.CUSTOMER)},
			{new User(0, "VlaSV", "password", "Vladislav", "Sevashko", "a@email.com", 5.0, Role.CUSTOMER)}
		});
	}

	@Test
	public void userIsValid() {
		assertFalse(Validator.userIsValid(user));
	}
}
