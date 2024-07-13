package com.rbc.uscm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rbc.uscm.entity.Country;

@Repository
public interface LocaleRepository extends JpaRepository<Country, Integer>{
	@Override
    Optional<Country> findById(Integer id);
	
	@Query(nativeQuery= true, value="SELECT * FROM COUNTRY_DETAILS WHERE code = ?")
	Optional<Country> findByCode(String Code);
}
