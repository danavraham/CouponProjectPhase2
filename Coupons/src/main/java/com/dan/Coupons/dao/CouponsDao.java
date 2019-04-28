package com.dan.Coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.utils.DateUtils;
import com.dan.Coupons.utils.JdbcUtils;

@Repository
public class CouponsDao implements ICouponsDao {

	public long createCoupon(Coupon coupon) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			// CouponID is defined as a primary key and auto incremented
			sqlStatement = "INSERT INTO coupons (CompanyID, Category, CouponTitle, CouponDescription, StartDate, EndDate, "
					+ "CouponAmount, CouponPrice, CouponImage) VALUES(?,?,?,?,?,?,?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, coupon.getCompanyID());
			preparedStatement.setString(2, coupon.getCategory().name());
			preparedStatement.setString(3, coupon.getCouponTitle());
			preparedStatement.setString(4, coupon.getDescription());
			preparedStatement.setString(5, coupon.getStartDate());
			preparedStatement.setString(6, coupon.getEndDate());
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());

			// Executing the update
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the coupon was created, returning the coupon ID
			if (resultSet.next()) {
				long couponID = resultSet.getLong(1);
				coupon.setCouponID(couponID);
				return couponID;
			}
			throw new ApplicationException(ErrorTypes.FAIL_TO_GENERATE_ID, "Couldent generate a Company ID");

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create new company");

		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCouponByID(long couponID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "DELETE FROM coupons WHERE CouponID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, couponID);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete coupon");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCouponsByCompanyID(long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "DELETE FROM coupons WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyID);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete coupons by company ID");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "UPDATE coupons SET CompanyID=?, Category=?, CouponTitle=?, CouponDescription=?, StartDate=?, EndDate=?, "
					+ "CouponAmount=?, CouponPrice=?, CouponImage=? WHERE CouponID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, coupon.getCompanyID());
			preparedStatement.setString(2, coupon.getCategory().name());
			preparedStatement.setString(3, coupon.getCouponTitle());
			preparedStatement.setString(4, coupon.getDescription());
			preparedStatement.setString(5, coupon.getStartDate());
			preparedStatement.setString(6, coupon.getEndDate());
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());
			preparedStatement.setLong(10, coupon.getCouponID());
			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not update coupon");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Coupon getOneCouponByID(long couponID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CouponID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setLong(1, couponID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// If it was successful, return the Company Object
			if (resultSet.next()) {

				// Send the ResultSet to the function that creates a Coupon Object from it, if
				// not - return NULL
				return createCouponFromResultSet(resultSet);
			} else {
				return null;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get a coupon by ID");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		;
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are coupons
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Coupon Object from it
				Coupon coupon = new Coupon();
				coupon = createCouponFromResultSet(resultSet);

				// Add the company created to the coupons ArrayList
				couponsList.add(coupon);

			}
			// Return the ArrayList of the coupons
			return couponsList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<Coupon> getAllCouponsByCompanyID(long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setLong(1, companyID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are coupons
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Coupon Object from it
				Coupon coupon = new Coupon();
				coupon = createCouponFromResultSet(resultSet);

				// Add the company created to the coupons ArrayList
				couponsList.add(coupon);
			}
			// Return the ArrayList of the coupons
			return couponsList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE Category=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setString(1, category.name());

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are coupons
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Coupon Object from it
				Coupon coupon = new Coupon();
				coupon = createCouponFromResultSet(resultSet);

				// Add the company created to the coupons ArrayList
				couponsList.add(coupon);
			}
			// Return the ArrayList of the coupons
			return couponsList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons by Category list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<Coupon> getAllCouponsByCategoryAndCompanyID(Category category, long companyID)
			throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE Category=? AND CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setString(1, category.name());
			preparedStatement.setLong(1, companyID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are coupons
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Coupon Object from it
				Coupon coupon = new Coupon();
				coupon = createCouponFromResultSet(resultSet);

				// Add the company created to the coupons ArrayList
				couponsList.add(coupon);

			}

			// Return the ArrayList of the coupons
			return couponsList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons by Category list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<Coupon> getAllCouponsByMaxPriceAndCompanyID(Double maxPrice, long companyID)
			throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CouponPrice<=? AND CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setDouble(1, maxPrice);
			preparedStatement.setLong(1, companyID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are coupons
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Coupon Object from it
				Coupon coupon = new Coupon();
				coupon = createCouponFromResultSet(resultSet);

				// Add the company created to the coupons ArrayList
				couponsList.add(coupon);

			}
			// Return the ArrayList of the coupons
			return couponsList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons by Category list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<Coupon> getAllCouponsByMaxPrice(Double maxPrice) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Coupon> couponsList = new ArrayList<Coupon>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CouponPrice<=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setDouble(1, maxPrice);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are coupons
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Coupon Object from it
				Coupon coupon = new Coupon();
				coupon = createCouponFromResultSet(resultSet);

				// Add the company created to the coupons ArrayList
				couponsList.add(coupon);
			}
			// Return the ArrayList of the coupons
			return couponsList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the coupons by max price list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public int getCouponAmountByID(long couponID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CouponID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setLong(1, couponID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// If it was successful, return the amount of coupons. if not throw Exception
			if (resultSet.next()) {
				int couponsAmount = resultSet.getInt("CouponAmount");
				// Send the ResultSet to the function that creates a Coupon Object from it, if
				// not - return NULL
				return couponsAmount;
			}
			throw new ApplicationException(ErrorTypes.FAIL_TO_GENERATE_ID, "Couldent get number of coupons left");

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get number of coupons left");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public boolean isCouponExistByID(long couponID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CouponID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, couponID);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If coupon exist return true
			if (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "could not find if coupon exist");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return false;
	}

	public boolean isCouponTitleExistForCompany(String couponTitle, long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM coupons WHERE CouponTitle=? AND CompanyID=? ";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, couponTitle);
			preparedStatement.setLong(2, companyID);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If coupon exist return true
			if (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR,
					"could not find if coupon title exist for company");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return false;
	}

	private Coupon createCouponFromResultSet(ResultSet resultSet) throws SQLException {
		Coupon coupon = new Coupon();
		// Creating the new Coupon to return
		coupon.setCouponID(resultSet.getLong("CouponId"));
		coupon.setCompanyID(resultSet.getLong("CompanyID"));
		coupon.setCategory(Category.valueOf(resultSet.getString("Category")));
		coupon.setCouponTitle(resultSet.getString("CouponTitle"));
		coupon.setDescription(resultSet.getString("CouponDescription"));
		coupon.setStartDate(resultSet.getString("StartDate"));
		coupon.setEndDate(resultSet.getString("EndDate"));
		coupon.setAmount(resultSet.getInt("CouponAmount"));
		coupon.setPrice(resultSet.getDouble("CouponPrice"));
		coupon.setImage(resultSet.getString("CouponImage"));

		return coupon;
	}

	public void deleteAllExpiredCoupons() throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			String sqlStatement = "DELETE FROM coupons WHERE EndDate<?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, DateUtils.getCurrentDate());

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed deleting expiered coupons");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

}
