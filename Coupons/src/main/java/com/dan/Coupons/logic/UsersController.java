package com.dan.Coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dan.Coupons.beans.User;
import com.dan.Coupons.beans.PostLoginCachedUserData;
import com.dan.Coupons.dao.UsersDao;
import com.dan.Coupons.enums.ClientType;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;

@Controller
public class UsersController {

	@Autowired
	private UsersDao usersDao;
	@Autowired
	private ICacheManager cacheManager;

	// REGULAR EXPRESSION Password check it is valid must contain 6-10
	// Charters, at least one letter, at least one digit
	@Autowired
	private static String passwordPattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,10}";

	public ClientType login(String userName, String password) throws ApplicationException {

		// Get the clientType by calling the 'login' method from the usersDao, and
		// returning the client type
		ClientType clientType = usersDao.login(userName, password);

		// If the user exist create the userData and the Token used for authentication
		// to put in the cache
		PostLoginCachedUserData userData = generateUserData(userName, clientType);
		int token = generateEncryptedToken(userName);
		cacheManager.put(token, userData);

		// Returning the client type if it was successful
		return clientType;
	}

	private PostLoginCachedUserData generateUserData(String userName, ClientType clientType) throws ApplicationException {
		// Get the userId from the user name
		long userID = this.usersDao.getUserIdByName(userName);
		// Create the UserData Object
		PostLoginCachedUserData userData = new PostLoginCachedUserData(userID, this.usersDao.getOneUserByID(userID).getCompanyId(), clientType);
		return userData;
	}

	private int generateEncryptedToken(String user) {
		String token = "Salt - junk data" + user + "Some extra text to make it harder to encrypt";

		// bcrypt  the token in Hash
		return token.hashCode();
	}

	public long createUser(User user) throws ApplicationException {
		// Validation of createUser option
		createUserValidation(user);

		// If validation went through correctly - call createUser method from usersDao
		return this.usersDao.createUser(user);
	}

	// Validations for updating the user
	private void createUserValidation(User user) throws ApplicationException {
		// If the user name of the user we try to create already exist in the DB(not
		// aloud)- throw exception
		if (this.usersDao.isUserExistsByName(user.getUserName())) {
			throw new ApplicationException(ErrorTypes.NAME_ALREADY_EXISTS,
					"Creating a user has failed. The user name chosen already exist in the DB");
		}

		// REGULAR EXPRESSION Password check it is valid must contain 6-10
		// Charters, at least one letter, at least one digit
		// If don't fit the rolls of password, throw exception
		if (!user.getPassword().matches(passwordPattern)) {
			throw new ApplicationException(ErrorTypes.INNCORECT_PASSWORD,
					"Cannot create user - password must contain 6-10 charaters, at least one letter, at least one digit");
		}

	}

	public void deleteUserByID(long userID) throws ApplicationException {
		// If the user ID of the user we try to delete does not exist in the DB- throw
		// exception
		if (!this.usersDao.isUserExistByID(userID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not delete user by ID, this ID does not exist in the DB");
		}
		// If the user ID of the user we try to delete exist in the DB- call the
		// 'deleteUserByID' method from the usersDao
		this.usersDao.deleteUserByID(userID);
	}

	public void updateUser(User user) throws ApplicationException {
		// Validation of updateUser option
		userUpdateValidation(user);

		// If validation went through correctly - call updateUser method from usersDao
		this.usersDao.updateUser(user);

	}

	// Validations for updating the user
	private void userUpdateValidation(User user) throws ApplicationException {

		// Check if user ID exist in the DB, if not - throw exception
		if (!this.usersDao.isUserExistByID(user.getUserID())) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Cannot update this user ID, the ID does not exist in the DB");
		}

		// Saving the current user name from the DB
		String userNameInDataBase = this.usersDao.getOneUserByID(user.getUserID()).getUserName();

		// Check if trying to update the user name(not aloud), if so - throw exception
		if (!userNameInDataBase.equals(user.getUserName())) {
			throw new ApplicationException(ErrorTypes.NON_REPLACEABLE_NAME,
					"You cannot change the user name in the update");
		}

		// REGULAR EXPRESSION Password check it is valid must contain 6-10
		// Charters, at least one letter, at least one digit
		// If don't fit the rolls of password, throw exception
		if (!user.getPassword().matches(passwordPattern)) {
			throw new ApplicationException(ErrorTypes.INNCORECT_PASSWORD,
					"Cannot update user - password must contain 6-10 charaters, at least one letter, at least one digit");

		}
	}

	public User getOneUserByID(long userID) throws ApplicationException {
		// If the user ID of the user we try to get does not exist in the DB- throw
		// exception
		if (!this.usersDao.isUserExistByID(userID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not get user by ID, this ID does not exist in the DB");
		}
		// If the user ID of the user we try to get exist in the DB- call the
		// 'getOneUserByID' method from the usersDao
		return this.usersDao.getOneUserByID(userID);
	}

	public List<User> getAllUsers() throws ApplicationException {
		// Call the 'getAllUsers' method from the usersDao
		return this.usersDao.getAllUsers();
	}
}
