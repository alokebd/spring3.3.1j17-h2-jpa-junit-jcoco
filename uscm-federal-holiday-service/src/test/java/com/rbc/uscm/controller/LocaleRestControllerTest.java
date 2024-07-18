package com.rbc.uscm.controller;

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
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.testutils.ApplicationTestConstants;
import com.rbc.uscm.testutils.HttpHelper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = UscmFederalHolidayServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocaleRestControllerTest {
	@Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;
                
    @Test
    public void test_1_should_create_country()  {
        //Given
    	String strCountry = "France";
    	String strCode = "fa";
        String url = String.format(ApplicationTestConstants.LOCALE_URL, port);
        CountryDto createRequest = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry(strCountry,strCode));
        HttpEntity<CountryDto> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<CountryDto> response = testRestTemplate.exchange(
        		url, 
        		HttpMethod.POST, 
        		request, 
        		CountryDto.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getCode(), "fa");
        assertEquals(response.getBody().getName(), "France");
    }
    
    @Test
    public void test_2_should_update_country()  {
        //Given
    	String strCountry = "Maxico";
    	String strCode = "ma";
    	String url = String.format(ApplicationTestConstants.LOCALE_URL, port);
        CountryDto createRequest = ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry(strCountry,strCode));
        HttpEntity<CountryDto> requestPost = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<CountryDto> postRes = testRestTemplate.exchange(
        		url, 
        		HttpMethod.POST, 
        		requestPost, 
        		CountryDto.class);
        
        String updatUri = String.format(ApplicationTestConstants.LOCALE_URL.concat("/").concat(strCode), port);
        Country country = ApplicationTestConstants.givenCountry();
    	country.setName(ApplicationTestConstants.UPDATED_NAME);
    	CountryDto updateRequest = ApplicationTestConstants.givenCountryDto(country);
        HttpEntity<CountryDto> reqPut = HttpHelper.getHttpEntity(updateRequest);
        //When
        ResponseEntity<CountryDto> putRes = testRestTemplate.exchange(
        		updatUri, 
        		HttpMethod.PUT, 
        		reqPut, 
        		CountryDto.class);
        //Then
        assertEquals(putRes.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(putRes.getBody());
        assertEquals(ApplicationTestConstants.UPDATED_NAME, putRes.getBody().getName());
    }
    
    
    @Test
    public void test_3_should_find_all_countries()  {
        //Given
        String url = String.format(ApplicationTestConstants.LOCALE_URL, port);
        //When
        ResponseEntity<List<CountryDto>> response = testRestTemplate.exchange(
        		url, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CountryDto>>() {});
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        
        List<CountryDto> countries = response.getBody();
        assertNotNull(countries.get(0));
    }
    
    
    @Test
    public void test_4_should_not_update_country_not_found_status()  {
        //Given
        String url = String.format(ApplicationTestConstants.LOCALE_URL.concat("/").concat("1"), port);
        Country country = ApplicationTestConstants.givenCountry();
    	country.setName("");
    	CountryDto createRequest = ApplicationTestConstants.givenCountryDto(country);
        HttpEntity<CountryDto> request = HttpHelper.getHttpEntity(createRequest);
        //When
        ResponseEntity<CountryDto> response = testRestTemplate.exchange(
        		url, 
        		HttpMethod.PUT, 
        		request, 
        		CountryDto.class);
        //Then
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    

}
