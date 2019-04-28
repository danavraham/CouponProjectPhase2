package com.dan.Coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dan.Coupons.beans.Company;
import com.dan.Coupons.dao.CompaniesDao;
import com.dan.Coupons.dao.CouponsDao;
import com.dan.Coupons.dao.PurchasesDao;
import com.dan.Coupons.dao.UsersDao;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;

@Controller
public class CompaniesController {

	@Autowired
	private CompaniesDao companiesDao;
	@Autowired
	private PurchasesDao purchasesDao;
	@Autowired
	private CouponsDao couponsDao;
	@Autowired
	private UsersDao usersDao;


	public long createCompany(Company company) throws ApplicationException {
		// If the company Name of the company we try to create already exist
		// in the DB (not aloud) - throw exception
		if (this.companiesDao.isCompanyExistByName(company.getCompanyName())) {
			throw new ApplicationException(ErrorTypes.NAME_ALREADY_EXISTS,
					"Could not create company, this company name already exist in the DB");
		}
		// If the company name of the don't exist in the DB- call the
		// 'createCompany' method from the companiesDao
		return this.companiesDao.createCompany(company);
	}

	public void deleteCompanyByID(long companyID) throws ApplicationException {
		// If the company ID of the company we try to delete does not exist in the DB-
		// throw exception
		if (!this.companiesDao.isCompanyExistByID(companyID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not delete company by ID, this ID does not exist in the DB");
		}
		// If the company ID of the company we try to delete exist in the DB-
		// call the following actions

		// 'deletePurchasesByCompanyID' method from the purchasesDao to delete purchase
		// history of the company
		this.purchasesDao.deletePurchasesByCompanyID(companyID);

		// 'deleteCouponsByCompanyID' method from the couponsDao to delete coupons of
		// the company
		this.couponsDao.deleteCouponsByCompanyID(companyID);

//		// 'deleteCouponsByCompanyID' method from the usersDao to delete users of
		// the deleted company
		this.usersDao.deleteUsersByCompanyID(companyID);

		// 'deleteCompanyByID' method from the companiesDao
		this.companiesDao.deleteCompanyByID(companyID);
	}

	public void updateCompany(Company company) throws ApplicationException {

		// Saving the current company name from the DB
		String companyNameInDataBase = this.companiesDao.getOneCompanyByID(company.getCompanyID()).getCompanyName();

		// Check if trying to update the company name(not aloud), if so - throw
		// exception
		if (!companyNameInDataBase.equals(company.getCompanyName())) {
			throw new ApplicationException(ErrorTypes.NON_REPLACEABLE_NAME,
					"You cannot change the company name in the update");
		}

		// If no exception was made - call the 'updateCompany' method from the
		// companiesDao
		this.companiesDao.updateCompany(company);

	}

	public Company getOneCompanyByID(long companyID) throws ApplicationException {
		// If the company ID of the company we try to get does not exist in the DB-
		// throw
		// exception
		if (!this.companiesDao.isCompanyExistByID(companyID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not get user by ID, this ID does not exist in the DB");
		}
		// If the company ID of the company we try to get exist in the DB- call the
		// 'getOneCompanyByID' method from the companiesDao
		return this.companiesDao.getOneCompanyByID(companyID);
	}

	public List<Company> getAllCompanies() throws ApplicationException {
		// Call the 'getAllCompanies' method from the companiesDao
		return this.companiesDao.getAllCompanies();
	}

}