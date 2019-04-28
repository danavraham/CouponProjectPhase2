package com.dan.Coupons.dao;

import java.util.List;

import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.exceptions.ApplicationException;

public interface ICouponsDao {
	
	public long createCoupon(Coupon coupon) throws ApplicationException;
	public void deleteCouponByID(long couponID) throws ApplicationException;
	public void deleteCouponsByCompanyID(long companyID) throws ApplicationException;
	public void updateCoupon(Coupon coupon) throws ApplicationException;
	public Coupon getOneCouponByID(long couponID) throws ApplicationException;
	public List<Coupon> getAllCoupons() throws ApplicationException;
	public List<Coupon> getAllCouponsByCompanyID(long companyID) throws ApplicationException;
	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException;
	public List<Coupon> getAllCouponsByCategoryAndCompanyID(Category category, long companyID) throws ApplicationException;
	public List<Coupon> getAllCouponsByMaxPriceAndCompanyID(Double maxPrice, long companyID) throws ApplicationException;
	public List<Coupon> getAllCouponsByMaxPrice(Double maxPrice) throws ApplicationException;
	public int getCouponAmountByID(long couponID) throws ApplicationException;
	public boolean isCouponExistByID(long couponID) throws ApplicationException;
	public boolean isCouponTitleExistForCompany(String couponTitle, long companyID) throws ApplicationException;
	public void deleteAllExpiredCoupons() throws ApplicationException;
	
	


}
