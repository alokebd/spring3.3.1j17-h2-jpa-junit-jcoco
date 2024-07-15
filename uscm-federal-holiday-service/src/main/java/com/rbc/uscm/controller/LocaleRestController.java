package com.rbc.uscm.controller;

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
import com.rbc.uscm.dto.CountryDto;
import com.rbc.uscm.entity.Country;
import com.rbc.uscm.exception.ResourceNotFoundException;
import com.rbc.uscm.service.LocaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value="Country-Resource")
public class LocaleRestController {
	private static final Logger logger = LoggerFactory.getLogger(LocaleRestController.class);
	
	@Autowired
	private LocaleService localeService;
		
	@ApiOperation(value = "All aviable locales.",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@GetMapping("/v1/countries")
	public ResponseEntity<List<CountryDto>> getAllCountries(){
		List<CountryDto> coutries = localeService.findAll();
		if (coutries == null || coutries.isEmpty()) {
			logger.error("REST Locale API .. no data found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(coutries, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Add locale.", response = CountryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created country"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@PostMapping("/v1/countries")
	public ResponseEntity<CountryDto> create(@RequestBody CountryDto countryDto) {
		logger.debug("Entered REST Locale API saving data ...");
	    CountryDto newCountryDto = localeService.save(countryDto);
	   return new ResponseEntity<>(newCountryDto, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Updatefor locale by cournty code.", response = CountryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@PutMapping("/v1/countries/{code}")
	public ResponseEntity<CountryDto> update(
			@PathVariable(value = "code") String code, 
			@RequestBody CountryDto countryDto) throws ResourceNotFoundException {
		logger.debug("Entered REST Locale API (" + code + ") ...");
		Optional<Country> dBcounty = localeService.findByCode(code);
		if (dBcounty.isPresent()) {
			int id = dBcounty.get().getId();
			CountryDto _countryDto =localeService.update(countryDto, id);
			return new ResponseEntity<>(_countryDto, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
