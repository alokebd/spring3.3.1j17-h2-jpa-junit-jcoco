package com.rbc.uscm.dto;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import com.rbc.uscm.testutils.ApplicationTestConstants;

@JsonTest
@RunWith(SpringRunner.class)
public class CountryDtoTest {
	
	@Autowired 
	private JacksonTester <CountryDto> json;
	
	private static final String JSON_TO_DESERIALIZE = 
			"{\"name\":\"" +ApplicationTestConstants.NAME +
			"\",\"code\":\"" +ApplicationTestConstants.CODE +
			"\"}";
	
		
	 @Test
	 public void  test_1_should_serialized_name() throws IOException {
		 CountryDto countryDto = new CountryDto();
		 	countryDto.setName(ApplicationTestConstants.NAME);
			countryDto.setCode(ApplicationTestConstants.CODE);
	    assertThat(json.write(countryDto))
	        .extractingJsonPathStringValue("@.name")
	        .isEqualTo(ApplicationTestConstants.NAME);
	 }
	 
	 @Test
	 public void  test_2_should_serialized_code() throws IOException {
		 CountryDto countryDto = new CountryDto();
		 	countryDto.setName(ApplicationTestConstants.NAME);
			countryDto.setCode(ApplicationTestConstants.CODE);
	    assertThat(json.write(countryDto))
	        .extractingJsonPathStringValue("@.code")
	        .isEqualTo(ApplicationTestConstants.CODE);
	 }
	 
	 @Test
     public void test_3_should_deserialized_name() throws IOException {
        assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getName()).isEqualTo(ApplicationTestConstants.NAME);
     }
	 
	 @Test
     public void test_4_should_deserialized_code() throws IOException {
        assertThat(this.json.parseObject(JSON_TO_DESERIALIZE).getCode()).isEqualTo(ApplicationTestConstants.CODE);
     }
	 
}
