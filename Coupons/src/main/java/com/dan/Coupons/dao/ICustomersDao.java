package com.dan.Coupons.dao;

import java.util.List;

import com.dan.Coupons.beans.Customer;
import com.dan.Coupons.exceptions.ApplicationException;

public interface ICustomersDao {

	public long createCustomer(Customer customer) throws ApplicationException;

	public void deleteCustomerByID(long customerID) throws ApplicationException;

	public void updateCustomer(Customer customer) throws ApplicationException;

	public Customer getOneCustomerByID(long customerID) throws ApplicationException;

	public List<Customer> getAllCustomers() throws ApplicationException;

	public boolean isCustomerExistByID(long customerID) throws ApplicationException;

}
