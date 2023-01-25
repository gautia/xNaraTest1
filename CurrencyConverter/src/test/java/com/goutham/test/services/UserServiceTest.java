package com.goutham.test.services;

import static com.goutham.test.TestConstants.TEST_USER_NAME;
import static com.goutham.test.TestConstants.TEST_USER_NAME2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.goutham.common.CurrencyConverterConstants;
import com.goutham.dao.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.goutham.entities.Address;
import com.goutham.entities.User;

public class UserServiceTest {

	private UserService userService;

	private UserRepository userRepository;

	PasswordEncoder passwordEncoder;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());
	}

	@Test
	public void registerUser_ValidUser_ReturnsRegisteredUser() {
		// Arrange
		User user = new User();
		user.setUsername(TEST_USER_NAME);
		user.setPassword(CurrencyConverterConstants.PASSWORD);
		Address address = new Address();
		user.setAddress(address);

		when(userRepository.save(any(User.class)))
				.thenAnswer((InvocationOnMock invocation) -> (User) invocation.getArguments()[0]);

		// Act
		User returnedUser = userService.registerUser(user);

		// Assert
		assertThat(TEST_USER_NAME).isEqualTo(returnedUser.getUsername());
	}

	@Test
	public void isUsernameAvailable_ExistingUsername_ReturnsFalse() {
		// Arrange
		when(userRepository.exists(TEST_USER_NAME2)).thenReturn(Boolean.TRUE);

		// Act
		boolean result = userService.isUsernameAvailable(TEST_USER_NAME2);

		// Assert
		assertThat(result).isFalse();

	}

	@Test
	public void isUsernameAvailable_NewUsername_ReturnsTrue() {
		// Arrange
		when(userRepository.exists(TEST_USER_NAME)).thenReturn(Boolean.FALSE);

		// Act
		boolean result = userService.isUsernameAvailable(TEST_USER_NAME);

		// Assert
		assertThat(result).isTrue();

	}

}
