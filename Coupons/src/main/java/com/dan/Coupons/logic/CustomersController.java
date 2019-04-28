package com.dan.Coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dan.Coupons.beans.Customer;
import com.dan.Coupons.dao.CustomersDao;
import com.dan.Coupons.dao.PurchasesDao;
import com.dan.Coupons.dao.UsersDao;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;

@Controller
public class CustomersController {

	@Autowired
	private CustomersDao customersDao;
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private PurchasesDao purchasesDao;


	public long createCustomer(Customer customer) throws ApplicationException {
		// First create the User
		this.usersDao.createUser(customer.getUser());

		// then createUser method from usersDao
		return this.customersDao.createCustomer(customer);
	}

	public void updateCustomer(Customer customer) throws ApplicationException {
		// first update User
		this.usersDao.updateUser(customer.getUser());

		// then update Customer
		this.customersDao.updateCustomer(customer);

	}

	public void deleteCustomerByID(long customerID) throws ApplicationException {
		// If the customer ID of the customer we try to delete does not exist in the DB-
		// throw exception
		if (!this.customersDao.isCustomerExistByID(customerID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not delete customer by ID, this ID does not exist in the DB");
		}
		// If the customer ID of the customer we try to delete exist in the DB- do the
		// following actions

		// call the 'deletePurchasesByCustomerID' method from the purchasesDao to delete
		// the purchase history of the customer
		this.purchasesDao.deletePurchasesByCustomerID(customerID);

		// call the 'deleteCustomerByID' method from the customersDao to delete the
		// customer
		this.customersDao.deleteCustomerByID(customerID);

		// call the 'deleteUserByID' method from the usersDao to delete the user with
		// the same ID
		this.usersDao.deleteUserByID(customerID);

	}

	public Customer getOneCustomerByID(long customerID) throws ApplicationException {
		// If the customer ID of the customer we try to get does not exist in the DB-
		// throw exception
		if (!this.customersDao.isCustomerExistByID(customerID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not get customer by ID, this ID does not exist in the DB");
		}
		// If the customer ID of the customer we try to get exist in the DB- call the
		// 'getOneCustomerByID' method from the customersDao
		return this.customersDao.getOneCustomerByID(customerID);
	}

	public List<Customer> getAllCustomers() throws ApplicationException {
		// Call the 'getAllCustomers' method from the customersDao
		return this.customersDao.getAllCustomers();
	}
}
