package com.dan.Coupons.jobs;

import java.util.TimerTask;

import com.dan.Coupons.dao.CouponsDao;
import com.dan.Coupons.dao.PurchasesDao;
import com.dan.Coupons.exceptions.ApplicationException;

public class DailyCleaningCouponsJob extends TimerTask {
	private CouponsDao couponsDao;
	private PurchasesDao purchasesDao;

	//---------------------CTOR----------------------
	public DailyCleaningCouponsJob() {
		this.couponsDao = new CouponsDao();
		this.purchasesDao = new PurchasesDao();
	}

	@Override
	public void run() {
		try {
			System.out.println("TimerTask running");
			couponsDao.deleteAllExpiredCoupons();
			purchasesDao.deleteAllExpiredCouponsPurchaces();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
