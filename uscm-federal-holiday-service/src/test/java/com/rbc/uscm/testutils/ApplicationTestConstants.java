package com.rbc.uscm.testutils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.dto.RequestHolidayDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.entity.Holiday;

public class ApplicationTestConstants {
    public static final String NAME = "Canada";
    public static final String CODE = "ca";
    public static final String UPDATED_NAME = "UPDATED_NAME";
    
    
    public static final int DAY =1;
    public static final int MONTH=1;
    public static final int YEAR=2024;
    public static final String DESCRIPTION ="Happy New Year";
    
    public static final int UPDATED_DAY=19;
    public static final int UPDATED_MONTH=2;
    public static final String UPDATED_DESCRIPTION ="Family day";
    
    public static final String ERROR_NOT_FOUND = "Resource not exist with id:";
    public static final String ERROR_ALREADY_FOUND = "Resource already exist with register id:";

    
    public static final String LOCALE_URL = "http://localhost:%s/api/v1/countries";
       
    public static Country givenCountry() {
    	Country country = new Country();
    	country.setId(null);
    	country.setName(ApplicationTestConstants.NAME);
    	country.setCode(ApplicationTestConstants.CODE);
        return country;
    }
    
    public static CountryDto givenCountryDto(Country country) {
    	return new ModelMapper().map(country, CountryDto.class);
    }
    
    public static LocalDate createLocalDate(int year, int month, int day) {
	     return LocalDate.of(year, month, day);
	}
   
    public static Holiday givenHoliday(Country country) {
    	Holiday holiday = new Holiday();
    	holiday.setId(null);
    	holiday.setCountry(country);
    	holiday.setDescription(ApplicationTestConstants.DESCRIPTION);
    	holiday.setOfficialDate(ApplicationTestConstants.createLocalDate(ApplicationTestConstants.YEAR, ApplicationTestConstants.MONTH, ApplicationTestConstants.DAY));
    	return holiday;
    }
    
    public static RequestHolidayDto createRequestHolidayDto() {
    	RequestHolidayDto requestHolidayDto = new RequestHolidayDto();
    	requestHolidayDto.setYear(ApplicationTestConstants.YEAR);
    	requestHolidayDto.setMonth(ApplicationTestConstants.MONTH);
    	requestHolidayDto.setDay(ApplicationTestConstants.DAY);
    	requestHolidayDto.setDescription(ApplicationTestConstants.DESCRIPTION);
    	return requestHolidayDto;
    }
    
    public static RequestHolidayDto createRequestHolidayDtoByHoliday(Holiday holiday) {
    	RequestHolidayDto requestHolidayDto = new RequestHolidayDto();
    	LocalDate localDate = holiday.getOfficialDate();
    	
    	requestHolidayDto.setYear(localDate.getYear());
    	requestHolidayDto.setMonth(localDate.getMonthValue());
    	requestHolidayDto.setDay(localDate.getDayOfMonth());
    	
    	requestHolidayDto.setDescription(holiday.getDescription());
    	return requestHolidayDto;
    }
    
    public static Holiday givenUpdateHoliday(Country country) {
    	Holiday holiday = new Holiday();
    	holiday.setCountry(country);
    	holiday.setDescription(ApplicationTestConstants.UPDATED_DESCRIPTION);
    	holiday.setOfficialDate(ApplicationTestConstants.createLocalDate(ApplicationTestConstants.YEAR, ApplicationTestConstants.UPDATED_MONTH, ApplicationTestConstants.UPDATED_DAY));
    	return holiday;
    }
      
    public static Country givenCountry(String name, String code) {
    	Country country = new Country();
    	country.setId(null);
    	country.setName(name);
    	country.setCode(code);
        return country;
    }
}
