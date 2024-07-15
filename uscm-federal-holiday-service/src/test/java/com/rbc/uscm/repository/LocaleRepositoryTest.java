package com.rbc.uscm.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.testutils.ApplicationTestConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocaleRepositoryTest {
	@Autowired
	private LocaleRepository localeRepository;

    @Autowired
    private TestEntityManager testEntityManager;
    
 
    private Country givenUpdatedCountry() {
    	Country cont = new Country();
    	cont.setName(ApplicationTestConstants.UPDATED_NAME);
        return cont;
    }
    
    
    @Test
    public void should_save_county() {
        //Given
    	Country country = ApplicationTestConstants.givenCountry();
    	country.setId(1);
        //When
    	country = localeRepository.save(country);
        //Then
    	Country actual = testEntityManager.find(Country.class, country.getId());
        assertEquals(actual, country);
    }

    @Test
    public void should_find_contry_by_id() {
        //Given
    	Country country = ApplicationTestConstants.givenCountry();
    	country.setId(1);
    	//cont = testEntityManager.persist(cont);
    	//testEntityManager.flush();
    	country = localeRepository.save(country);
    	Country testEnt = testEntityManager.find(Country.class, country.getId());
    	
        //When
        Optional<Country> actual = localeRepository.findById(testEnt.getId());
        //Then
        assertNotEquals(actual, Optional.empty());
        assertEquals(actual.get(), testEnt);
    }
    
    @Test
    public void should_find_contry_by_code() {
        //Given
    	Country country = ApplicationTestConstants.givenCountry();
    	country.setId(1);
    	country = localeRepository.save(country);
    	Country testEnt = testEntityManager.find(Country.class, country.getId());
        //When
        Optional<Country> actual = localeRepository.findByCode(testEnt.getCode());
        //Then
        assertNotEquals(actual, Optional.empty());
        assertEquals(actual.get(), testEnt);
    }

    @Test
    public void should_find_all_countries() {
        //Given
    	Country country = ApplicationTestConstants.givenCountry();
    	country.setId(1);
      	country = localeRepository.save(country);
    	Country testEnt = testEntityManager.find(Country.class, country.getId());
        //When
        List<Country> countries = localeRepository.findAll();
        //Then
        assertThat(countries).contains(testEnt);
    }

    @Test
    public void should_update_country() {
        //Given
    	Country country = ApplicationTestConstants.givenCountry();
    	country.setId(1);
    	country = localeRepository.save(country);
    	Country testEnt = testEntityManager.find(Country.class, country.getId());
    	
    	Country updatedCout = givenUpdatedCountry();
    	updatedCout.setId(testEnt.getId());
        //When
    	updatedCout = localeRepository.save(updatedCout);
        //Then
    	Country actual = testEntityManager.find(Country.class, country.getId());
        assertEquals(actual, updatedCout);

    }


}
