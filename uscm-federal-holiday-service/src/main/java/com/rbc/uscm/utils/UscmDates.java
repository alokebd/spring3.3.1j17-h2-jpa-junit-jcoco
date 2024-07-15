package com.rbc.uscm.utils;

import java.time.LocalDate;

public final class UscmDates {
	 
	 private UscmDates() { 
	 }
	 
	 /**
	  * 
	  * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
      * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
      * @param dayOfMonth  the day-of-month to represent, from 1 to 31
	  * @return LocalDate
	  */
	 public static LocalDate newLocalDate(int year, int month, int day) {
	     return LocalDate.of(year, month, day);
	 }

}
