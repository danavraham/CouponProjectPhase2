package com.dan.Coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dan.Coupons.beans.Coupon;
import com.dan.Coupons.beans.Purchase;
import com.dan.Coupons.enums.Category;
import com.dan.Coupons.exceptions.ApplicationException;
import com.dan.Coupons.logic.PurchasesController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {

	@Autowired
	private PurchasesController purchasesController;

	@PostMapping
	public void createPurchase(@RequestBody Purchase purchase) throws ApplicationException {
		this.purchasesController.createPurchase(purchase);
		System.out.println("createPurchase" + purchase);
	}

	@GetMapping("/{purchaseID}")
	public Coupon getOnePurchaseByID(@PathVariable("purchaseID") long purchaseID) throws ApplicationException {
		System.out.println("getOnePurchaseByID" + purchaseID);
		return this.purchasesController.getOnePurchaseByPurchaseID(purchaseID);
	}

	@GetMapping("/allPurchases")
	public List<Coupon> getAllPurchases() throws ApplicationException {
		System.out.println("getAllPurchases");
		return this.purchasesController.getAllPurchases();
	}

	@GetMapping("/customerPurchaces")
	public List<Coupon> getPurchasesByCustomerID(@PathVariable("customerID") long customerID)
			throws ApplicationException {
		return this.purchasesController.getAllPurchasesByCustomerID(customerID);
	}

	@GetMapping("/couponPurchacesByCategory")
	public List<Purchase> getAllPurchasesByCouponID(@PathVariable("couponID") long couponID)
			throws ApplicationException {
		return this.purchasesController.getAllPurchasesByCouponID(couponID);
	}

	@GetMapping("/byCustomerAndCategory")
	public List<Coupon> getAllPurchasesByCustomerIDAndCategory(@RequestParam("customerID") long customerID,
			@RequestParam("category") Category category) throws ApplicationException {
		return this.purchasesController.getAllPurchasesByCustomerIDAndCategory(customerID, category);
	}

	@GetMapping("/byCustomerAndMaxPrice")
	public List<Coupon> getAllPurchasesByCustomerIDAndCategory(@RequestParam("customerID") long customerID,
			@RequestParam("maxPrice") double maxPrice) throws ApplicationException {
		return this.purchasesController.getAllPurchasesByCustomerIDAndMaxPrice(customerID, maxPrice);
	}

}
