package com.rbc.uscm.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.entity.Holiday;
import com.rbc.uscm.testutils.ApplicationTestConstants;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HolidayRepositoryTest {
	@Autowired
	private HolidayRepository holidayRepository;
	@Autowired
	private LocaleRepository localeRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    
    
    private Holiday givenHoliday() {
    	Holiday holiday = new Holiday();
    	holiday.setId(0);
    	holiday.setDescription(ApplicationTestConstants.DESCRIPTION);
    	holiday.setOfficialDate(ApplicationTestConstants.createLocalDate(ApplicationTestConstants.YEAR, ApplicationTestConstants.MONTH, ApplicationTestConstants.DAY));
    	return holiday;
    }
    
    private Holiday givenUpdateHoliday() {
    	Holiday holiday = new Holiday();
    	holiday.setId(0);
    	holiday.setDescription(ApplicationTestConstants.UPDATED_DESCRIPTION);
    	holiday.setOfficialDate(ApplicationTestConstants.createLocalDate(ApplicationTestConstants.YEAR, ApplicationTestConstants.UPDATED_MONTH, ApplicationTestConstants.UPDATED_DAY));
    	return holiday;
    }
    
    @Test
    public void should_save_holiday() {
        //Given
    	Country testData = ApplicationTestConstants.givenCountry();
    	testData.setId(1);
    	Country country = localeRepository.save(testData);
    	Holiday holiday = givenHoliday();
    	holiday.setCountry(country);
        //When
    	holiday = holidayRepository.save(holiday);
        //Then
    	Holiday actual = testEntityManager.find(Holiday.class, holiday.getId());
        assertEquals(actual, holiday);
    }

    @Test
    public void should_find_holiday_by_id() {
        //Given
    	Country testData = ApplicationTestConstants.givenCountry();
    	testData.setId(1);
    	Country country = localeRepository.save(testData);
    	Holiday holiday = givenHoliday();
    	holiday.setCountry(country);
    	
    	holiday = holidayRepository.save(holiday);
    	Holiday testEnt = testEntityManager.find(Holiday.class, holiday.getId());
    	
        //When
        Optional<Holiday> actual = holidayRepository.findById(testEnt.getId());
        //Then
        assertNotEquals(actual, Optional.empty());
        assertEquals(actual.get(), testEnt);
    }
    
    @Test
    public void should_find_holiday_by_contry_id() {
        //Given
    	Country testData = ApplicationTestConstants.givenCountry();
    	testData.setId(1);
    	Country country = localeRepository.save(testData);
    	Holiday holiday = givenHoliday();
    	holiday.setCountry(country);
    	
    	holiday = holidayRepository.save(holiday);
    	Holiday testEnt = testEntityManager.find(Holiday.class, holiday.getId());
        //When
        List<Holiday> actual = holidayRepository.findByCountryId(testEnt.getCountry().getId());
        //Then
        assertNotEquals(actual, Optional.empty());
        assertEquals(actual.get(0), testEnt);
    }

    @Test
    public void should_find_all_holidays() {
        //Given
    	Country testData = ApplicationTestConstants.givenCountry();
    	testData.setId(1);
    	Country country = localeRepository.save(testData);
    	Holiday holiday = givenHoliday();
    	holiday.setCountry(country);
    	holiday = holidayRepository.save(holiday);
    	Holiday testEnt = testEntityManager.find(Holiday.class, holiday.getId());
        //When
        List<Holiday> countries = holidayRepository.findAll();
        //Then
        assertThat(countries).contains(testEnt);
    }

    @Test
    public void should_update_holiday() {
        //Given
    	Country testData = ApplicationTestConstants.givenCountry();
    	testData.setId(1);
    	Country country = localeRepository.save(testData);
    	Holiday holiday = givenHoliday();
    	holiday.setCountry(country);
    	holiday = holidayRepository.save(holiday);
       	Holiday testEnt = testEntityManager.find(Holiday.class, holiday.getId());
    	
       	Holiday updatedHol = givenUpdateHoliday();
       	updatedHol.setId(testEnt.getId());
        //When
    	updatedHol = holidayRepository.save(updatedHol);
        //Then
    	Holiday actual = testEntityManager.find(Holiday.class, holiday.getId());
        assertEquals(actual, updatedHol);

    }
}
