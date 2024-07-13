package com.rbc.uscm.utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public final class UscmDates {
	 public static final String DATE_FORMAT = "MMMM dd, yyyy, EEEE";
	 private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	 
	 private UscmDates() {
		 
	 }
	 
	 public static LocalDate newLocalDate() {
	        return LocalDate.now();
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
	 
	 public static LocalDate newLocalDate(int year, Month month, int day) {
	        return LocalDate.of(year, month, day);
	 }

}
