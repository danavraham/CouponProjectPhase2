package com.dan.Coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dan.Coupons.beans.Customer;
import com.dan.Coupons.beans.User;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.utils.JdbcUtils;

@Repository
public class CustomersDao implements ICustomersDao {

	public long createCustomer(Customer customer) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			// CustomerID is defined as a primary key and auto incremented
			sqlStatement = "INSERT INTO customers (CustomerID, CustomerFirstName, CustomerLastName) VALUES(?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, customer.getUser().getUserID());
			preparedStatement.setString(2, customer.getCustomerFirstName());
			preparedStatement.setString(3, customer.getCustomerLastName());

			// Executing the update
			preparedStatement.executeUpdate();
			
			// If the customer was created, returning the customer ID
			if (customer.getUser().getUserID() != 0) {
				long customerID = customer.getUser().getUserID();
				customer.setCustomerID(customerID);
				return customerID;
			} else {
				throw new ApplicationException(ErrorTypes.FAIL_TO_GENERATE_ID, "Could'nt generate a Customer ID");
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create new customer");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCustomerByID(long customerID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "DELETE FROM customers WHERE customerID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, customerID);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete customer");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void updateCustomer(Customer customer) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "UPDATE customers SET CustomerFirstName=?, CustomerLastName=? WHERE CustomerID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, customer.getCustomerFirstName());
			preparedStatement.setString(2, customer.getCustomerLastName());
			preparedStatement.setLong(3, customer.getUser().getUserID());

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

	public Customer getOneCustomerByID(long customerID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM customers WHERE CustomerID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, customerID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// If it was successful, return the Customer Object
			if (resultSet.next()) {

				// Send the ResultSet to the function that creates a Customer Object from it, if
				// not - return NULL
				return createCustomerFromResultSet(resultSet);
			} else {
				return null;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get a customer by ID");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Customer> customersList = new ArrayList<Customer>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM customers";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();


			// Run on a while loop as long as there are customers
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a User Object from it
				Customer customer = new Customer();
				customer = createCustomerFromResultSet(resultSet);

				// Add the user created to the users ArrayList
				customersList.add(customer);
			} 
			// Return the ArrayList of the users
			return customersList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the users list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public boolean isCustomerExistByID(long customerID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM customers WHERE CustomerID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, customerID);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If user exist return true
			if (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "could not find if customer ID exist");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return false;
	}

	private Customer createCustomerFromResultSet(ResultSet resultSet) throws SQLException, ApplicationException {
		UsersDao usersDao = new UsersDao();
		Customer customer = new Customer();
		// Creating the Customers User Object from the identical customerID
		User customerUser = new User();
		customerUser = usersDao.getOneUserByID(resultSet.getLong("CustomerID"));
		// Creating the Customer properties
		customer.setCustomerID(resultSet.getLong("CustomerID"));
		customer.setCustomerFirstName(resultSet.getString("CustomerFirstName"));
		customer.setCustomerLastName(resultSet.getString("CustomerLastName"));
		customer.setUser(customerUser);
	
		return customer;
	}

}
