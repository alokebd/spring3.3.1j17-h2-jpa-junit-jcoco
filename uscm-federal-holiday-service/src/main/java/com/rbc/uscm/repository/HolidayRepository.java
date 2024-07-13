package com.rbc.uscm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rbc.uscm.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer>{

    @Override
    Optional<Holiday> findById(Integer id);
    
    List<Holiday> findByCountryId(int coutryId);

}
