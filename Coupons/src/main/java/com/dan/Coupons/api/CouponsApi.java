package com.dan.Coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.logic.CouponsController;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {

	@Autowired
	private CouponsController couponsController;

	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponsController.createCoupon(coupon);
		System.out.println("createCoupon" + coupon);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponsController.updateCoupon(coupon);
		System.out.println("updateCoupon" + coupon);
	}

	@DeleteMapping("/{couponID}")
	public void deleteCoupon(@PathVariable("couponID") long couponID) throws ApplicationException {
		this.couponsController.deleteCouponByID(couponID);
		System.out.println("deleteCouponByID");
	}

	@GetMapping("/{couponID}")
	public Coupon getCoupon(@PathVariable("couponID") long couponID) throws ApplicationException {
		System.out.println("getOneCouponByID");
		return this.couponsController.getOneCouponByID(couponID);
	}

	@GetMapping("/allCoupons")
	public List<Coupon> getAllCoupons() throws ApplicationException {
		System.out.println("getAllCoupons");
		return this.couponsController.getAllCoupons();
	}

	@GetMapping("/byCategory")
	// @RequestParam = get the value from the query string
	public List<Coupon> getCouponsByCategory(@RequestParam("category") Category category) throws ApplicationException {
		System.out.println(category);
		return this.couponsController.getAllCouponsByCategory(category);
	}

	@GetMapping("/byCompany")
	public List<Coupon> getAllCouponsByCompanyID(@RequestParam("companyID") long companyID)
			throws ApplicationException {
		return this.couponsController.getAllCouponsByCompanyID(companyID);
	}

	@GetMapping("/byMaxPrice")
	public List<Coupon> getAllCouponsByMaxPrice(Double maxPrice) throws ApplicationException {
		return this.couponsController.getAllCouponsByMaxPrice(maxPrice);
	}

	@GetMapping("/byCategoryAndCompany")
	public List<Coupon> getAllCouponsByCategoryAndCompanyID(@RequestParam("category") Category category,
			@RequestParam("companyId") long companyId) throws ApplicationException {
		return this.couponsController.getAllCouponsByCategoryAndCompanyID(category, companyId);
	}

	@GetMapping("/byMaxPriceAndCompany")
	public List<Coupon> getAllCouponsByMaxPriceAndCompanyID(@RequestParam("maxPrice") double maxPrice,
			@RequestParam("companyId") long companyId) throws ApplicationException {
		return this.couponsController.getAllCouponsByMaxPriceAndCompanyID(maxPrice, companyId);
	}

}
