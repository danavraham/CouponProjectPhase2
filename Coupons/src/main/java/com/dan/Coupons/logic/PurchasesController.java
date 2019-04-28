package com.dan.Coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.beans.Purchase;
import com.dan.Coupons.dao.CouponsDao;
import com.dan.Coupons.dao.PurchasesDao;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.utils.DateUtils;

@Controller
public class PurchasesController {

	@Autowired
	private PurchasesDao purchasesDao;
	@Autowired
	private CouponsDao couponsDao;


	public long createPurchase(Purchase purchase) throws ApplicationException {
		// Validation of createPurchase option
		createPurchaseValidation(purchase);

		// If validation went through correctly - call createPurchase method from
		// couponsDao and save the purchaseID
		long purchaseID = this.purchasesDao.createPurchase(purchase);

		// Reduce the amount of coupons available
		this.purchasesDao.updateCouponAmountAfterPurchase(purchase.getCouponID(), purchase.getAmount());

		// If it is successful - return purchase ID
		return purchaseID;
	}

	// Validations for creating a purchase
	private void createPurchaseValidation(Purchase purchase) throws ApplicationException {
		// Saving the providers specific coupon amount available
		int amountOfCouponsAvailable = this.couponsDao.getCouponAmountByID(purchase.getCouponID());
		// saving the coupon from the purchase
		Coupon wantedCoupon = this.couponsDao.getOneCouponByID(purchase.getCouponID());

		// Validation 1- If the coupon amount available in the DB is lower than the
		// amount the customer wants to purchase - throw exception
		if (amountOfCouponsAvailable < purchase.getAmount()) {
			throw new ApplicationException(ErrorTypes.NOT_ENOUGH_COUPONS_LEFT,
					"You wanted to Purchase " + purchase.getAmount()
							+ ", Not enough coupons left to purchase this amount. You can purchase up to: "
							+ amountOfCouponsAvailable);
		}

		// Validation 2- Check if the coupon has expired, if so - throw exception
		if (DateUtils.isCurrentDateAfterEndDate(wantedCoupon.getEndDate())) {
			throw new ApplicationException(ErrorTypes.COUPON_EXPIERED,
					"Cannot purchase coupon, the wanted coupon has Expiered");
		}

	}

	public Coupon getOnePurchaseByPurchaseID(long purchaseID) throws ApplicationException {
		// If the purchase ID of the purchase we try to get does not exist in the DB-
		// throw
		// exception
		if (!this.purchasesDao.isPurchaseExistByID(purchaseID)) {
			throw new ApplicationException(ErrorTypes.ID_DONT_EXIST,
					"Could not get purchace by ID, this ID does not exist in the DB");
		}
		// If the purchase ID of the purchase we try to get exist in the DB- call the
		// 'getOnePurchaseByPurchaseID' method from the purchasesDao
		return this.purchasesDao.getOnePurchaseByPurchaseID(purchaseID);
	}

	public List<Coupon> getAllPurchases() throws ApplicationException {
		// Call the 'getAllPurchases' method from the purchasesDao
		return this.purchasesDao.getAllPurchases();
	}

	public List<Coupon> getAllPurchasesByCustomerID(long customerID) throws ApplicationException {
		// Call the 'getAllPurchasesByCustomerID' method from the purchasesDao
		return this.purchasesDao.getAllPurchasesByCustomerID(customerID);
	}

	public List<Purchase> getAllPurchasesByCouponID(long couponID) throws ApplicationException {
		// Call the 'getAllPurchasesByCouponID' method from the purchasesDao
		return this.purchasesDao.getAllPurchasesByCouponID(couponID);
	}

	public List<Coupon> getAllPurchasesByCustomerIDAndCategory(long customerID, Category category)
			throws ApplicationException {
		// Call the 'getAllPurchasesByCustomerIDAndCategory' method from the
		// purchasesDao
		return this.purchasesDao.getAllPurchasesByCustomerIDAndCategory(customerID, category);
	}

	public List<Coupon> getAllPurchasesByCustomerIDAndMaxPrice(long customerID, Double maxPrice)
			throws ApplicationException {
		// Call the 'getAllPurchasesByCustomerIDAndMaxPrice' method from the
		// purchasesDao
		return this.purchasesDao.getAllPurchasesByCustomerIDAndMaxPrice(customerID, maxPrice);
	}

}
