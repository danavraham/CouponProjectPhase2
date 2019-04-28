package com.dan.Coupons.dao;

import java.util.List;

import com.dan.Coupons.beans.User;
import com.dan.Coupons.enums.ClientType;
import com.dan.Coupons.exceptions.ApplicationException;

public interface IUsersDao {

	public ClientType login(String userName, String password) throws ApplicationException;

	public long createUser(User user) throws ApplicationException;

	public void updateUser(User user) throws ApplicationException;

	public void deleteUserByID(long userID) throws ApplicationException;

	public void deleteUsersByCompanyID(long companyID) throws ApplicationException;

	public User getOneUserByID(long useryID) throws ApplicationException;

	public List<User> getAllUsers() throws ApplicationException;

	public boolean isUserExistByID(long userID) throws ApplicationException;

	public boolean isUserExistsByName(String userName) throws ApplicationException;

}
