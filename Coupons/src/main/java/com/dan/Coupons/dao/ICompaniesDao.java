package com.dan.Coupons.dao;

import java.util.List;

import com.dan.Coupons.beans.Company;
import com.dan.Coupons.exceptions.ApplicationException;

public interface ICompaniesDao {

	public long createCompany(Company company) throws ApplicationException;

	public void deleteCompanyByID(long companyID) throws ApplicationException;

	public void updateCompany(Company company) throws ApplicationException;

	public Company getOneCompanyByID(long companyID) throws ApplicationException;

	public List<Company> getAllCompanies() throws ApplicationException;

	public boolean isCompanyExistByID(long companyID) throws ApplicationException;

	public boolean isCompanyExistByName(String companyName) throws ApplicationException;

}
