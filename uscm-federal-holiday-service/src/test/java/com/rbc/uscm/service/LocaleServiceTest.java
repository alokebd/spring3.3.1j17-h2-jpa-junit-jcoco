package com.rbc.uscm.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.rbc.uscm.repository.LocaleRepository;

@SpringBootTest
public class LocaleServiceTest {
	@InjectMocks
    private LocaleService localeService;

    @Mock
    private LocaleRepository localeRepository;
    
    //TODO
}
