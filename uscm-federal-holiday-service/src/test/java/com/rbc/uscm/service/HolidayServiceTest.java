package com.rbc.uscm.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.rbc.uscm.repository.HolidayRepository;

@SpringBootTest
public class HolidayServiceTest {
	@InjectMocks
    private HolidayService holidayService;

    @Mock
    private HolidayRepository holidayRepository;
    
    //TODO
}
