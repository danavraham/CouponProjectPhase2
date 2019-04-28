package com.dan.Coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.dao.CouponsDao;
import com.dan.Coupons.dao.PurchasesDao;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.utils.DateUtils;


@Controller
public class CouponsController {

	@Autowired
	private CouponsDao couponsDao;
	@Autowired
	private PurchasesDao purchasesDao;


	public long createCoupon(Coupon coupon) throws ApplicationException {
		// Validation of createCoupon option
		createCouponValidation(coupon);

		// If validation went through correctly - call createCoupon method from
		// couponsDao
		return this.couponsDao.createCoupon(coupon);
	}

	private void createCouponValidation(Coupon coupon) throws ApplicationException {
		// If the company already has the same Title for existing coupon(not aloud) -
		// throw exception
		if (this.couponsDao.isCouponTitleExistForCompany(coupon.getCouponTitle(), coupon.getCompanyID())) {
			throw new ApplicationException(ErrorTypes.COUPON_TITLE_EXIST_FOR_COMPANY,
					"Could not create coupon, The company already have a coupon with the same title");
		}

		// If the coupon price is not more than 0 throw exception
		if (!(coupon.getPrice() > 0)) {
			throw new ApplicationException(ErrorTypes.INVALID_PRICE,
					"Cannot create a coupon, price must be more than 0");
		}

		// If the coupon amount is not more than 0 throw exception
		if (!(coupon.getAmount() > 0)) {
			throw new ApplicationException(ErrorTypes.INVALID_AMOUNT,
					"Cannot create a coupon, coupon amount must be more than 0");
		}

		// Check if coupon end date has passed and if the start date is after the end
		// date, if so - throw exception
		if (DateUtils.isCurrentDateAfterEndDate(coupon.getEndDate())
				|| DateUtils.isDate1AfterDate2(coupon.getStartDate(), coupon.getEndDate())) {
			throw new ApplicationException(ErrorTypes.INVALID_DATES, "You have entered wrong dates, try again");
		}

	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {
		// Validation of updateCoupon option
		updateCouponValidation(coupon);

		// If validation went through correctly - call updateCoupon method from
		// couponsDao
		this.couponsDao.updateCoupon(coupon);

	}

	private void updateCouponValidation(Coupon coupon) throws ApplicationException {
		// Saving the current coupon companyID from the DB
		long couponCompanyIDInDataBase = this.couponsDao.getOneCouponByID(coupon.getCouponID()).getCompanyID();

		// Check if trying to update the companyID of coupon(not aloud), if so - throw
		// exception
		if (!(couponCompanyIDInDataBase == (coupon.getCompanyID()))) {
			throw new ApplicationException(ErrorTypes.CANNOT_UPDATE_COMPANY_ID, "Coupons company ID cannot be changed");
		}

		// validating with the creation validation
		createCouponValidation(coupon);

	}

	public void deleteCouponByID(long couponID) throws ApplicationException {
		// If the coupon ID of the coupon we try to delete does not exist in the DB-
		// throw exception
		if (!this.couponsDao.isCouponExistByID(couponID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not delete coupon by ID, this ID does not exist in the DB");
		}
		// If the coupon ID of the company we try to delete exist in the DB-
		// call the following actions

		// 'deletePurchasesByCouponID' method from the purchasesDao to delete purchase
		// history of the coupon
		this.purchasesDao.deletePurchasesByCouponID(couponID);

		// 'deleteCouponByID' method from the couponsDao to delete the coupons
		this.couponsDao.deleteCouponByID(couponID);

	}

	public Coupon getOneCouponByID(long couponID) throws ApplicationException {
		// If the coupon ID o we try to get does not exist in the DB- throw
		// exception
		if (!this.couponsDao.isCouponExistByID(couponID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not get coupon by ID, this ID does not exist in the DB");
		}
		// If the coupon ID we try to get exist in the DB- call the
		// 'getOneCompanyByID' method from the companiesDao
		return this.couponsDao.getOneCouponByID(couponID);
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		// Call the 'getAllCoupons' method from the couponsDao
		return this.couponsDao.getAllCoupons();
	}

	public List<Coupon> getAllCouponsByCompanyID(long companyID) throws ApplicationException {
		// Call the 'getAllCouponsByCompanyID' method from the couponsDao
		return this.couponsDao.getAllCouponsByCompanyID(companyID);
	}

	public List<Coupon> getAllCouponsByCategoryAndCompanyID(Category category, long companyID)
			throws ApplicationException {
		// Call the 'getAllCouponsByCategoryAndCompanyID' method from the couponsDao
		return this.couponsDao.getAllCouponsByCategoryAndCompanyID(category, companyID);
	}

	public List<Coupon> getAllCouponsByMaxPriceAndCompanyID(Double maxPrice, long companyID)
			throws ApplicationException {
		// Call the 'getAllCouponsByMaxPriceAndCompanyID' method from the couponsDao
		return this.couponsDao.getAllCouponsByMaxPriceAndCompanyID(maxPrice, companyID);
	}

	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {
		// Call the 'getAllCouponsByCategory' method from the couponsDao
		return this.couponsDao.getAllCouponsByCategory(category);
	}

	public List<Coupon> getAllCouponsByMaxPrice(Double maxPrice) throws ApplicationException {
		// Call the 'getAllCouponsByMaxPrice' method from the couponsDao
		return this.couponsDao.getAllCouponsByMaxPrice(maxPrice);
	}

}
