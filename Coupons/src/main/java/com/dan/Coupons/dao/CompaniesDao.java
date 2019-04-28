package com.dan.Coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dan.Coupons.beans.Company;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.utils.JdbcUtils;

@Repository
public class CompaniesDao implements ICompaniesDao {

	public long createCompany(Company company) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			// CompanyID is defined as a primary key and auto incremented
			sqlStatement = "INSERT INTO companies (CompanyName, CompanyEmail, CompanyWebsite) VALUES(?,?,?)";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getEmail());
			preparedStatement.setString(3, company.getWebsite());

			// Executing the update
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			// If the company was created, returning the company ID
			if (resultSet.next()) {
				long companyID = resultSet.getLong(1);
				company.setCompanyID(companyID);
				return companyID;
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

	public void deleteCompanyByID(long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "DELETE FROM companies WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyID);

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete company");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void updateCompany(Company company) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "UPDATE companies SET CompanyName=?, CompanyEmail=?, CompanyWebsite=? WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getEmail());
			preparedStatement.setString(3, company.getWebsite());
			preparedStatement.setLong(4, company.getCompanyID());

			// Executing the update
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not update company");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Company getOneCompanyByID(long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM companies WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data

			preparedStatement.setLong(1, companyID);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// If it was successful, return the Company Object
			if (resultSet.next()) {

				// Send the ResultSet to the function that creates a Company Object from it, if
				// not - return NULL
				return createCompanyFromResultSet(resultSet);
			} else {
				return null;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get a company by ID");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";
		List<Company> companiesList = new ArrayList<Company>();

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM companies";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Execute the Query and saving the results
			ResultSet resultSet = preparedStatement.executeQuery();

			// Run on a while loop as long as there are companies
			while (resultSet.next()) {
				// Send the ResultSet to the function that creates a Company Object from it
				Company company = new Company();
				company = createCompanyFromResultSet(resultSet);

				// Add the company created to the companies ArrayList
				companiesList.add(company);

			} 
			// Return the ArrayList of the companies
			return companiesList;

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Could not get the companies list");
		} finally {

			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public boolean isCompanyExistByID(long companyID) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM companies WHERE CompanyID=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setLong(1, companyID);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If company exist return true
			if (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "could not find if company ID exist");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return false;
	}

	public boolean isCompanyExistByName(String companyName) throws ApplicationException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sqlStatement = "";

		try {
			// Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			// Creating the SQL query
			sqlStatement = "SELECT * FROM companies WHERE CompanyName=?";

			// Combining between the syntax and our connection
			preparedStatement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, companyName);

			// Executing the query and saving the DB response in the resultSet.
			ResultSet result = preparedStatement.executeQuery();

			// If company exist return true
			if (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and
			// notifies a level above.
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "could not find if company Name exist");
		} finally {
			// Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		return false;
	}

	private Company createCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setCompanyID(resultSet.getLong("CompanyID"));
		company.setCompanyName(resultSet.getString("CompanyName"));
		company.setEmail(resultSet.getString("CompanyEmail"));
		company.setWebsite(resultSet.getString("CompanyWebsite"));
		return company;
	}

}
