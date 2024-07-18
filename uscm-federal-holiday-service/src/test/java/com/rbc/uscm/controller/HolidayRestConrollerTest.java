package com.rbc.uscm.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import com.rbc.uscm.UscmFederalHolidayServiceApplication;
import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.dto.ReponseHolidayDto;
import com.rbc.uscm.dto.RequestHolidayDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.testutils.ApplicationTestConstants;
import com.rbc.uscm.testutils.HttpHelper;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = UscmFederalHolidayServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HolidayRestConrollerTest{
	@Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;
    
    private static final String FEDERAL_HOLIDAY_URL = "http://localhost:%s/api/v1/holidays/";
    
    private RequestHolidayDto updatedRequestHolidayDto() {
    	RequestHolidayDto requestHolidayDto = new RequestHolidayDto();
    	requestHolidayDto.setYear(ApplicationTestConstants.YEAR);
    	requestHolidayDto.setMonth(ApplicationTestConstants.UPDATED_MONTH);
    	requestHolidayDto.setDay(ApplicationTestConstants.UPDATED_DAY);
    	requestHolidayDto.setDescription(ApplicationTestConstants.UPDATED_DESCRIPTION);
    	return requestHolidayDto;
    }
    
   
    
    @Test
    public void test_1_should_create_country()  {
        //Given
    	String countryURL = String.format(ApplicationTestConstants.LOCALE_URL, port);
    	String strCountry = "USA";
    	String strCode = "usa";
        CountryDto createCountryDto = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry(strCountry, strCode));
        HttpEntity<CountryDto> requestCountryDto = HttpHelper.getHttpEntity(createCountryDto);
        //When
        ResponseEntity<CountryDto> countryRes = testRestTemplate.exchange(
        		countryURL, 
        		HttpMethod.POST, 
        		requestCountryDto, 
        		CountryDto.class);
        //Then
        assertEquals(countryRes.getStatusCode(), HttpStatus.OK);
        assertNotNull(countryRes.getBody());
        
    	//GIVEN
    	String uri = FEDERAL_HOLIDAY_URL.concat("/").concat(strCode);
        String url = String.format(uri, port);
        RequestHolidayDto createRequest = ApplicationTestConstants.createRequestHolidayDto();
        HttpEntity<RequestHolidayDto> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<ReponseHolidayDto> response = testRestTemplate.exchange(
        		url, 
        		HttpMethod.POST, 
        		request, 
        		ReponseHolidayDto.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getDescription(), ApplicationTestConstants.DESCRIPTION);
    }
    
    
    @Test
    public void test_2_should_update_country()  {
    	String countryURL = String.format(ApplicationTestConstants.LOCALE_URL, port);
        CountryDto createCountryDto = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry());
        HttpEntity<CountryDto> requestCountryDto = HttpHelper.getHttpEntity(createCountryDto);
        //When
        ResponseEntity<CountryDto> countryRes = testRestTemplate.exchange(
        		countryURL, 
        		HttpMethod.POST, 
        		requestCountryDto, 
        		CountryDto.class);
        //Then
        assertEquals(countryRes.getStatusCode(), HttpStatus.OK);
        
    	//GIVEN
    	String uri = FEDERAL_HOLIDAY_URL.concat("/").concat(ApplicationTestConstants.CODE);
        String url = String.format(uri, port);
        RequestHolidayDto createRequest = ApplicationTestConstants.createRequestHolidayDto();
        HttpEntity<RequestHolidayDto> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<ReponseHolidayDto> response = testRestTemplate.exchange(
        		url, 
        		HttpMethod.POST, 
        		request, 
        		ReponseHolidayDto.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
          
        //Given
    	String updateUri = FEDERAL_HOLIDAY_URL.concat("/")
    			.concat(ApplicationTestConstants.CODE)
    			.concat("/")
    			.concat(response.getBody().getHolidayId().toString());
    	 //Given
    	 String updateUrl = String.format(updateUri, port);
         RequestHolidayDto updatedDto = updatedRequestHolidayDto();
         HttpEntity<RequestHolidayDto> updatedReq = HttpHelper.getHttpEntity(updatedDto);
         
       //When
        ResponseEntity<ReponseHolidayDto> updatedRes = testRestTemplate.exchange(
        		updateUrl, 
         		HttpMethod.PUT, 
         		updatedReq, 
         		ReponseHolidayDto.class);
         //Then
         assertEquals(updatedRes.getStatusCode(), HttpStatus.CREATED);
         
         assertNotNull(updatedRes.getBody());
    }
    
    @Test
    public void test_3_should_find_all_holidays_by_country_code()  {
        //Given - country
    	String countryURL = String.format(ApplicationTestConstants.LOCALE_URL, port);
    	String strCountry = "UK";
    	String strCode = "uk";
        CountryDto createCountryDto = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry(strCountry, strCode));
        HttpEntity<CountryDto> requestCountryDto = HttpHelper.getHttpEntity(createCountryDto);
        //When
        ResponseEntity<CountryDto> countryRes = testRestTemplate.exchange(
        		countryURL, 
        		HttpMethod.POST, 
        		requestCountryDto, 
        		CountryDto.class);
        //Then
        assertEquals(countryRes.getStatusCode(), HttpStatus.OK);
        //Given - holiday info for country
    	String uri = FEDERAL_HOLIDAY_URL.concat("/").concat(strCode);
        String url = String.format(uri, port);
        RequestHolidayDto createRequest = ApplicationTestConstants.createRequestHolidayDto();
        HttpEntity<RequestHolidayDto> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<ReponseHolidayDto> response = testRestTemplate.exchange(
        		url, 
        		HttpMethod.POST, 
        		request, 
        		ReponseHolidayDto.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        
        //Given
    	uri = FEDERAL_HOLIDAY_URL.concat("/").concat(strCode);
        url = String.format(uri, port);
       //When
        ResponseEntity<List<ReponseHolidayDto>> responseList = testRestTemplate.exchange(
        		url, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<ReponseHolidayDto>>() {});
        //Then
        assertEquals(responseList.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseList.getBody().get(0));
    }
    
    
    @Test
    public void test_4_should_not_found_find_all_holidays_by_country_code()  {
        //Given
    	String countryURL = String.format(ApplicationTestConstants.LOCALE_URL, port);
    	String strCountry = "EPC";
    	String strCode = "lp";
        CountryDto createCountryDto = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry(strCountry, strCode));
        HttpEntity<CountryDto> requestCountryDto = HttpHelper.getHttpEntity(createCountryDto);
        //When
        ResponseEntity<CountryDto> countryRes = testRestTemplate.exchange(
        		countryURL, 
        		HttpMethod.POST, 
        		requestCountryDto, 
        		CountryDto.class);
        //Then
        assertEquals(countryRes.getStatusCode(), HttpStatus.OK);
        //Given
    	String uri = FEDERAL_HOLIDAY_URL.concat("/").concat("11");
        String url = String.format(uri, port);
        RequestHolidayDto createRequest = ApplicationTestConstants.createRequestHolidayDto();
        HttpEntity<RequestHolidayDto> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<ReponseHolidayDto> response = testRestTemplate.exchange(
        		url, 
        		HttpMethod.POST, 
        		request, 
        		ReponseHolidayDto.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        
        //Given - country code not present
    	uri = FEDERAL_HOLIDAY_URL.concat("/").concat("22");
        url = String.format(uri, port);
       //When
        ResponseEntity<List<ReponseHolidayDto>> responseList = testRestTemplate.exchange(
        		url, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<ReponseHolidayDto>>() {});
        //Then
        assertEquals(responseList.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
    @Test
    public void test_5_should_return_404_when_update()  {
    	 //Given - country
    	String url1 = String.format(ApplicationTestConstants.LOCALE_URL, port);
    	String strCountry = "Italy";
    	String strCode = "it";
        CountryDto createCountryDto = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry(strCountry, strCode));
        HttpEntity<CountryDto> requestCountryDto = HttpHelper.getHttpEntity(createCountryDto);
        //When
        ResponseEntity<CountryDto> countryRes = testRestTemplate.exchange(
        		url1, 
        		HttpMethod.POST, 
        		requestCountryDto, 
        		CountryDto.class);
        //Then
        assertEquals(countryRes.getStatusCode(), HttpStatus.OK);
        assertNotNull(countryRes.getBody());
        
        //Given - holiday info by country code
    	String uri2 = FEDERAL_HOLIDAY_URL.concat("/").concat(strCode);
        String url2 = String.format(uri2, port);
        RequestHolidayDto createHoliday = ApplicationTestConstants.createRequestHolidayDto();
        HttpEntity<RequestHolidayDto> createHolidatReq = HttpHelper.getHttpEntity(createHoliday);
        //When
        ResponseEntity<ReponseHolidayDto> createHolidayResp = testRestTemplate.exchange(
        		url2, 
        		HttpMethod.POST, 
        		createHolidatReq, 
        		ReponseHolidayDto.class);
        
        //Then
        assertEquals(createHolidayResp.getStatusCode(), HttpStatus.OK);
        
        //Given - country code not present but holiday id is
        String uri3 = FEDERAL_HOLIDAY_URL.concat("/")
    			.concat("2k")
    			.concat("/")
    			.concat(createHolidayResp.getBody().getHolidayId().toString());
        String url3 = String.format(uri3, port);
        RequestHolidayDto updateHolidayRequest = updatedRequestHolidayDto();
        HttpEntity<RequestHolidayDto> updateHolidayReq = HttpHelper.getHttpEntity(updateHolidayRequest);
        //When
        ResponseEntity<ReponseHolidayDto> updatedHolidayRes = testRestTemplate.exchange(
        		url3, 
         		HttpMethod.PUT, 
         		updateHolidayReq, 
         		ReponseHolidayDto.class);
        //Then
        assertEquals(updatedHolidayRes.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
