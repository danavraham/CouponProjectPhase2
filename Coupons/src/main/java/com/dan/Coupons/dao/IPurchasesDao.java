package com.dan.Coupons.dao;

import java.util.List;

import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.beans.Purchase;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.exceptions.ApplicationException;

public interface IPurchasesDao {

	public long createPurchase(Purchase purchase) throws ApplicationException;

	public void deletePurchasesByCompanyID(long companyID) throws ApplicationException;

	public void deletePurchasesByCustomerID(long customerID) throws ApplicationException;

	public void deletePurchasesByCouponID(long couponID) throws ApplicationException;

	public void updateCouponAmountAfterPurchase(long couponId, int amountToReduce) throws ApplicationException;

	public Coupon getOnePurchaseByPurchaseID(long purchaseID) throws ApplicationException;

	public List<Coupon> getAllPurchases() throws ApplicationException;

	public List<Coupon> getAllPurchasesByCustomerID(long customerID) throws ApplicationException;

	public List<Purchase> getAllPurchasesByCouponID(long couponID) throws ApplicationException;

	public List<Coupon> getAllPurchasesByCustomerIDAndCategory(long customerID, Category category)
			throws ApplicationException;

	public List<Coupon> getAllPurchasesByCustomerIDAndMaxPrice(long customerID, Double maxPrice)
			throws ApplicationException;

	public boolean isPurchaseExistByID(long purchaseID) throws ApplicationException;

}
