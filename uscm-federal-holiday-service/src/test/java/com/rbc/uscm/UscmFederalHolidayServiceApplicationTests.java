package com.rbc.uscm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;

import com.rbc.uscm.controller.HolidayRestConroller;
import com.rbc.uscm.controller.LocaleRestController;

@SpringBootTest
class UscmFederalHolidayServiceApplicationTests {

	@Autowired
	private LocaleRestController localeRestController;
	@Autowired
	private HolidayRestConroller holidayRestConroller;
	
	@Test
	void contextLoads() {
		Assertions.assertThat(localeRestController).isNotNull();
		
		Assertions.assertThat(holidayRestConroller).isNotNull();
	}
	
	@Test
	public void applicationStarts() {
		UscmFederalHolidayServiceApplication.main(new String[] {});
	 }

}
