package com.dan.Coupons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dan.Coupons.enums.ErrorTypes;
import com.dan.Coupons.exceptions.ApplicationException;

public class DateUtils {

	public static boolean isCurrentDateAfterEndDate(String EndDate) throws ApplicationException {
		// Check if the coupon has expired
		return isDate1AfterDate2(getCurrentDate(), EndDate);
	}

	public static String getCurrentDate() {
		// Formating the date to the right DB format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		// Get the current date
		Date today = new Date();
		// Formatting the date to string
		String currentDate = dateFormat.format(today);

		return currentDate;
	}

	public static boolean isDate1AfterDate2(String Date1, String Date2) throws ApplicationException {
		try {
			// Formating the date to the right DB format
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			// Formating the dates from string to date type.
			Date date1 = dateFormat.parse(Date1);
			Date date2 = dateFormat.parse(Date2);
			// Check if date1 is before date2
			if (date1.after(date2)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Parsing the date has failed");
		}
		return false;
	}

}
