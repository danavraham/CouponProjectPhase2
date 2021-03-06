package com.dan.Coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dan.Coupons.beans.User;
import com.dan.Coupons.enums.ClientType;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.utils.JdbcUtils;

@Repository
public class UsersDao implements IUsersDao {

	public ClientType login(String userName, String password) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM users WHERE UserName = ? && Password = ?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If user like that does not exist -throw Login Error
			if (!result.next()) {
				throw new ApplicationException(ErrorTypes.LOGIN_FAILED, "Login failed, please try again");
			}
			// Else return the client type
			ClientType clientType = ClientType.valueOf((result.getString("Type")));
			return clientType;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Login has failed");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public long createUser(User user) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			// There are 2 types of users, Customers with no company ID and Company workers
			// with company ID
			if (user.getCompanyId() == null) {

				// User from type Customer
				sqlStatement = "INSERT INTO users (UserName, Password, Type) VALUES(?,?,?)";

			} else {

				// User from type Company worker
				sqlStatement = "INSERT INTO users (UserName, Password, Type, CompanyID) VALUES(?,?,?,?)";
			}

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getType().name());
			// If it the User type of Company, add company ID to the statement
			if (!(user.getCompanyId() == null)) {
				preparedStatement.setLong(4, user.getCompanyId());
			}

			// Executing the update
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the user was created, returning the user ID
			if (resultSet.next()) {
				long userID = resultSet.getLong(1);
				user.setUserID(userID);
				return userID;
			}
			throw new ApplicationException(ErrorTypes.FAIL_TO_GENERATE_ID, "Couldent generate a User ID");

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create new user");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void updateUser(User user) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "UPDATE users SET UserName=?, Password=? WHERE UserID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setLong(3, user.getUserID());

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not update user");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteUserByID(long userID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "DELETE FROM users WHERE UserID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, userID);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete user");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteUsersByCompanyID(long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "DELETE FROM users WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyID);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete users by company ID");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public User getOneUserByID(long userID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM users WHERE UserID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setLong(1, userID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// If it was successful, return the User Object
			if (resultSet.next()) {

				// Send the ResultSet to the function that creates a User Object from it, if
				// not - return NULL
				return createUserFromResultSet(resultSet);
			} else {
				return null;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get a user by ID");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<User> getAllUsers() throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<User> usersList = new ArrayList<User>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM users";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are users
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a User Object from it
				User user = new User();
				user = createUserFromResultSet(resultSet);

				// Add the user created to the users ArrayList
				usersList.add(user);
			}
			// Return the ArrayList of the users
			return usersList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the users list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public boolean isUserExistByID(long userID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM users WHERE UserID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, userID);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If user exist return true
			if (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "could not find if user ID exist");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return false;
	}

	public boolean isUserExistsByName(String userName) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM users WHERE UserName = ?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userName);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If exist - return true
			if (result.next()) {
				return true;
			}
			// If don't exist - return false
			return false;
		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR,
					"Failed to check if customer name exist in the DB");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public long getUserIdByName(String userName) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT UserID FROM users WHERE UserName=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setString(1, userName);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// If it was successful, return the User Object
			if (!resultSet.next()) {
				
				// Return user ID if there is one, if not - return NULL
				return resultSet.getLong("UserID");
			} else {
				throw new ApplicationException(ErrorTypes.FAIL_TO_GENERATE_ID, "Couldent generate a User ID");
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get a userID by Name");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUserID(resultSet.getLong("UserID"));
		user.setUserName(resultSet.getString("UserName"));
		user.setPassword(resultSet.getString("Password"));
		// Check if the User don't have a CompanyID (a customer), and if so, set the
		// company ID to NULL (as a result set will automatically return 0 when it is
		// NULL)
		Long companyIdValue = resultSet.getLong("CompanyID");
		user.setCompanyId(resultSet.wasNull() ? null : companyIdValue);
		user.setType(ClientType.valueOf(resultSet.getString("Type")));

		return user;
	}

}
