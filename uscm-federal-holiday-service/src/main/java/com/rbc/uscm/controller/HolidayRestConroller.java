package com.rbc.uscm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rbc.uscm.dto.ReponseHolidayDto;
import com.rbc.uscm.dto.RequestHolidayDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.entity.Holiday;
import com.rbc.uscm.exception.ResourceNotFoundException;
import com.rbc.uscm.service.HolidayService;
import com.rbc.uscm.service.LocaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api("/api")
public class HolidayRestConroller {
	private static final Logger logger = LoggerFactory.getLogger(HolidayRestConroller.class);

	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private LocaleService localeService;
	
	@ApiOperation(value = "Holidays list - find by locale.")
	@GetMapping("/v1/holidays/{countryCode}")
	public ResponseEntity<List<ReponseHolidayDto>> getHolidaysByCountryCode(@PathVariable(value = "countryCode") String code) {
		Optional<Country> county = localeService.findByCode(code);
		List<ReponseHolidayDto> list = new ArrayList<ReponseHolidayDto>();
		if (county.isPresent()) {
			list = holidayService.getAllHolidays(county.get().getId());
		}
		if (list.isEmpty()) {
			logger.error("Entered REST to pull Holiday  API (" + code + ") ...not having data");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Add holiday for locale.")
	@PostMapping("/v1/holidays/{code}")
	public ResponseEntity<ReponseHolidayDto> saveHolidayByCountryCode(@PathVariable(value = "code") String code,
			@RequestBody RequestHolidayDto holidayResource) {
		Optional<Country> county = localeService.findByCode(code);
		if (county.isPresent()) {
			ReponseHolidayDto newHolidayDto = holidayService.save(holidayResource, county.get());
			return new ResponseEntity<>(newHolidayDto, HttpStatus.CREATED);
		}
		logger.error("Entered REST Holiday saving API (" + code + ") ...");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Update holiday by holiday Id.")
	@PutMapping("/v1/holidays/{code}/{holidayId}")
	public ResponseEntity<ReponseHolidayDto> updateHolidayById(
			@PathVariable(value = "code") String code, 
			@PathVariable(value = "holidayId") int id,
			@RequestBody RequestHolidayDto requestHolidayDto) throws ResourceNotFoundException {
		logger.debug("Entered REST Holiday updating API (" + code + ") ...");
		Optional<Country> county = localeService.findByCode(code);
		Optional<Holiday> holiday = holidayService.findById(id);
		if (county.isPresent() && holiday.isPresent()) {
			ReponseHolidayDto newHolidayDto =holidayService.update(requestHolidayDto, county.get(), holiday.get());
			return new ResponseEntity<>(newHolidayDto, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
