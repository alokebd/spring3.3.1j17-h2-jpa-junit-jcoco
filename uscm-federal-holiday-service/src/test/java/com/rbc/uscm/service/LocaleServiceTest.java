package com.rbc.uscm.service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.exception.ResourceAlreadyExistsException;
import com.rbc.uscm.repository.LocaleRepository;
import com.rbc.uscm.testutils.ApplicationTestConstants;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LocaleServiceTest {
	    
    @Mock
    private LocaleRepository localeRepository;
    
    @InjectMocks
    private LocaleService localeService;

    
    @DisplayName("JUnit test for country save() method")
    @Test
    public void should_save_country() {
        //Given
    	Country repoCountry = localeRepository.save(ApplicationTestConstants.givenCountry());
        //When
    	when(repoCountry).thenReturn(ApplicationTestConstants.givenCountry());
        //Then
        assertEquals(ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry()), localeService.save(ApplicationTestConstants.givenCountryDto(ApplicationTestConstants.givenCountry())));
    }

    @DisplayName("JUnit test for country findAll() method")
    @Test
    public void should_find_all_countries() {
        //Given - country
        //When
    	when(localeRepository.findAll()).thenReturn(Collections.singletonList(ApplicationTestConstants.givenCountry()));
        //Then
    	List<CountryDto> serviceListDto =localeService.findAll();
 
        assertThat(serviceListDto).isNotNull();
        assertThat(serviceListDto.size()).isEqualTo(1);

    }
    
    @DisplayName("JUnit test for country findByCode() method")
    @Test
    public void should_find_country_by_code() {
        //Given - country
        //When
    	when(localeRepository.findByCode(ApplicationTestConstants.CODE)).thenReturn(Optional.of(ApplicationTestConstants.givenCountry()));
        //Then
        assertEquals(ApplicationTestConstants.givenCountry(), localeService.findByCode(ApplicationTestConstants.CODE).get());
    }
    
    @DisplayName("JUnit test for country update() method")
    @Test
    public void should_update_country() {
        //Given - country
    	Country country = ApplicationTestConstants.givenCountry();
    	country.setName(ApplicationTestConstants.UPDATED_NAME);
    	country.setId(1);
        //When
         when(localeRepository.save(country)).thenReturn(country);
    
        //Then
        assertEquals(ApplicationTestConstants.givenCountryDto(country), localeService.update(ApplicationTestConstants.givenCountryDto(country), 1));
    }
   
    

}
