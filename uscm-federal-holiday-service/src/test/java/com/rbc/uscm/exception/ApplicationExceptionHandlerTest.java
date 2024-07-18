package com.rbc.uscm.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.dto.RequestHolidayDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.entity.Holiday;
import com.rbc.uscm.service.HolidayService;
import com.rbc.uscm.service.LocaleService;
import com.rbc.uscm.testutils.ApplicationTestConstants;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@SpringBootTest
public class ApplicationExceptionHandlerTest {
	 @InjectMocks
	 private ApplicationExceptionHandler applicationExceptionHandler;
	 //private ErrorResponse errorResponse;

	 @Autowired
	 private LocaleService localeService;
	 @Autowired
	 private HolidayService holidayService;
	 
	 @BeforeEach
	 public void init(){
	        MockitoAnnotations.openMocks(this);
	 }
	 
	 
	 @Test
	 public void test_1_HandleResourceNotFoundException(){
        ResourceNotFoundException ex = new ResourceNotFoundException(ApplicationTestConstants.ERROR_NOT_FOUND + 1000);
        //expected 
        ResponseEntity<ErrorResponse> expectedResponse = 
        		ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        
        //Actual response
        ResponseEntity<ErrorResponse> actualResponse = applicationExceptionHandler.handleResourceNotFoundException(ex);
        
        //assert the response
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        
        ErrorResponse expectedResultBody = expectedResponse.getBody();
        ErrorResponse actualResultBody = actualResponse.getBody();
        
        if(expectedResultBody != null && actualResultBody !=null) {
           assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
        }
	  }
	 
	 
	  @Test
	  public void test_2_HandleResourceExistsException(){
	        
		 ResourceAlreadyExistsException ex=new ResourceAlreadyExistsException(ApplicationTestConstants.ERROR_ALREADY_FOUND + 123);
	     //expected response
	     ResponseEntity<ErrorResponse> expectedResponse = 
	        		ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()));
	        
	     //actual
	     ResponseEntity<ErrorResponse> actualResponse = applicationExceptionHandler.handleResourceAlreadyExistsException(ex);
	     //assert the response
	     assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
	     ErrorResponse expectedResultBody = expectedResponse.getBody();
	     ErrorResponse actualResultBody = actualResponse.getBody();
	        
	     if(expectedResultBody != null && actualResultBody !=null) {
	          assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
	      }
	  }
	  
	  
	  @Test
	  public void test_3_HandleConstraintViolationException() {
		  CountryDto countryDto = new CountryDto();
		  countryDto.setName("");
		  countryDto.setCode("usa");
	
		  ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
        	  localeService.save(countryDto);
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceNotFoundException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("Resource agruments name and city are required.", responseEntity.getBody().getMessage());
          }
	  }
	  
	  @Test
	  public void test_4_country_not_samve_resource_already_exists_Exception() {
		  CountryDto countryDto = new CountryDto();
		  countryDto.setName("abcdef");
		  countryDto.setCode("abc");
		  localeService.save(countryDto);
		  
		  ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
        	  localeService.save(countryDto);
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceAlreadyExistsException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("Resource already exists.", responseEntity.getBody().getMessage());
              assertEquals(HttpStatus.CONFLICT.value(), responseEntity.getBody().getStatusCode());
          }
	  }
	  
	  @Test
	  public void test_5_MethodArgumentTypeMismatchException(){
	       MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
	       when(ex.getMessage()).thenReturn("Given request param is not supported");
	       // Call the method and assert the response
	       ResponseEntity<ErrorResponse> actualResult = applicationExceptionHandler.handleMethodArgumentTypeMismatchException(ex);
	       assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
	       ErrorResponse actualResultBody = actualResult.getBody();
	       if(actualResultBody != null) {
	           assertEquals("Given request param is not supported", actualResult.getBody().getMessage());
	       }
	  }
	  
	  
	  @Test
	  public void test_6_handle_constraint_violation_exception_For_Update_LocaleService() {
		  CountryDto countryDto = new CountryDto();
		  countryDto.setName("");
		  countryDto.setCode("usa");
	
		  ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
        	  localeService.update(countryDto, 100);
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceNotFoundException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("CountryDto values (name and code) are requied. They are non blank.", responseEntity.getBody().getMessage());
          }
	  }
	  
	  
	  @Test
	  public void test_7_resource_not_found_For_For_FindAll_LocaleService() {
		 // List<Country> entities = new ArrayList<Country>();
		  ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
        	  localeService.findAll();
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceNotFoundException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("Resource not found", responseEntity.getBody().getMessage());
              assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().getStatusCode());
          }
	  }
	  
	  
	  @Test
	  public void test_8_handle_constraint_violation_exception_For_save_Holiday_SaveService() {
		  RequestHolidayDto dto = new RequestHolidayDto();
		  dto.setYear(0);
		  dto.setMonth(13);
		  dto.setDay(32);
		  dto.setDescription("sss");
		  ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			  holidayService.save(dto, null);
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceNotFoundException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("RequestHolidayDto values are requied. Year must be non zero and lenght 4, Month must be between 1 and 12 according to calendar Year, Day must be between 1 and 31 according to calendar Month", responseEntity.getBody().getMessage());
          }
	  }
	  
	  @Test
	  public void test_9_resource_not_found_For_GetAll_Holidays_LocaleService() {
		  ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			  holidayService.getAllHolidays(-1);
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceNotFoundException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("Resource not found", responseEntity.getBody().getMessage());
          }
	  }
	  
	  @Test
	  public void test_10_handle_constraint_violation_exception_For_update_Holiday_SaveService() {
		  RequestHolidayDto dto = new RequestHolidayDto();
		  dto.setYear(0);
		  dto.setMonth(12);
		  dto.setDay(31);
		  dto.setDescription("sss");
		  Country country =ApplicationTestConstants.givenCountry();
	    	country.setId(1);
	       Holiday holiday =ApplicationTestConstants.givenUpdateHoliday(country);
	    	holiday.setId(1);
		  ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			  holidayService.update(dto, country, holiday);
          });
          
          ResponseEntity<ErrorResponse> responseEntity = applicationExceptionHandler.handleResourceNotFoundException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          
          if(actualResultBody != null) {
              assertEquals("RequestHolidayDto values are requied. Year must be non zero and lenght 4, Month must be between 1 and 12 according to calendar Year, Day must be between 1 and 31 according to calendar Month", responseEntity.getBody().getMessage());
          }
	  }
	  
	  

}
