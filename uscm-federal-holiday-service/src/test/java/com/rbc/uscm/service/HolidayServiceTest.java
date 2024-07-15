package com.rbc.uscm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.rbc.uscm.dto.ReponseHolidayDto;
import com.rbc.uscm.dto.RequestHolidayDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.entity.Holiday;
import com.rbc.uscm.repository.HolidayRepository;
import com.rbc.uscm.testutils.ApplicationTestConstants;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HolidayServiceTest {
	@InjectMocks
    private HolidayService holidayService;

    @Mock
    private HolidayRepository holidayRepository;
     
    
    private ReponseHolidayDto generateReponseHolidayDto(Holiday holiday) {
    	ReponseHolidayDto reponseHolidayDto = new ReponseHolidayDto();
    	reponseHolidayDto.setHolidayId(holiday.getId());
    	reponseHolidayDto.setDescription(holiday.getDescription());
    	reponseHolidayDto.setOfficialDate(holiday.getOfficialDate());
    	return reponseHolidayDto;
    }
           
    
    @DisplayName("JUnit test for holiday save() method")
    @Test
    public void should_save_holiday_by_country_code() {
        //Given
    	Holiday repoHoliday = holidayRepository.save(ApplicationTestConstants.givenHoliday(ApplicationTestConstants.givenCountry()));
        //When
    	when(repoHoliday).thenReturn(ApplicationTestConstants.givenHoliday(ApplicationTestConstants.givenCountry()));
        //Then
        assertEquals(generateReponseHolidayDto(ApplicationTestConstants.givenHoliday(ApplicationTestConstants.givenCountry())), 
        		holidayService.save(ApplicationTestConstants.createRequestHolidayDto(), ApplicationTestConstants.givenCountry()));
    }
    
    @DisplayName("JUnit test for holdays getAllHolidays() method")
    @Test
    public void should_find_all_holidays_by_country_Id() {
        //Given - 
    	Country country =ApplicationTestConstants.givenCountry();
    	country.setId(1);
        //When
    	when(holidayRepository.findByCountryId(country.getId())).thenReturn(Collections.singletonList(ApplicationTestConstants.givenHoliday(country)));
        //Then
    	List<ReponseHolidayDto> serviceListDto =holidayService.getAllHolidays(country.getId());
 
        assertThat(serviceListDto).isNotNull();
        assertThat(serviceListDto.size()).isEqualTo(1);

    }
    
    @DisplayName("JUnit test for holiday findById() method")
    @Test
    public void should_find_holiday_by_id() {
        //Given
    	Country country =ApplicationTestConstants.givenCountry();
    	country.setId(1);
    	Holiday holiday =ApplicationTestConstants.givenHoliday(country);
    	holiday.setId(1);
        //When
    	when(holidayRepository.findById(holiday.getId())).thenReturn(Optional.of(holiday));
        //Then
        assertEquals(holiday, holidayService.findById(country.getId()).get());
    }
    
    @DisplayName("JUnit test for holiday update() method")
    @Test
    public void should_update_holiday() {
        //Given - country
    	Country country =ApplicationTestConstants.givenCountry();
    	country.setId(1);
    	Holiday holiday =ApplicationTestConstants.givenUpdateHoliday(country);
    	holiday.setId(1);
        //When
         when(holidayRepository.save(holiday)).thenReturn(holiday);
    
        //Then
        assertEquals(generateReponseHolidayDto(holiday), holidayService.update(ApplicationTestConstants.createRequestHolidayDtoByHoliday(holiday), country, holiday));
    }
    
}
